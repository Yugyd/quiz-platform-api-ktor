package com.yugyd.quiz.data.themes

import com.yugyd.quiz.core.logger.Logger
import com.yugyd.quiz.core.toggles.CachedThemeContentLocalToggle
import com.yugyd.quiz.data.themes.db.ThemeDatabase
import com.yugyd.quiz.data.themes.mapper.ThemeMapper
import com.yugyd.quiz.domain.ai.AiRepository
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel
import com.yugyd.quiz.domain.theme.models.list.ThemeListModel
import com.yugyd.quiz.domain.theme.service.ThemesRepository
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin

internal class ThemesRepositoryImpl(
    private val themeDatabase: ThemeDatabase,
    private val themeMapper: ThemeMapper,
    private val cachedThemeContentLocalToggle: CachedThemeContentLocalToggle,
    private val logger: Logger,
) : ThemesRepository {

    override suspend fun getThemes(
        content: ContentModel,
    ): List<ThemeListModel> {
        return themeDatabase.getThemes().map(themeMapper::mapToListModel)
    }

    override suspend fun getThemes(
        content: ContentModel,
        parentThemeId: Int,
    ): List<ThemeListModel> {
        return themeDatabase.getThemes(parentId = parentThemeId).map(themeMapper::mapToListModel)
    }

    override suspend fun getThemeDetail(
        id: Int,
        recreate: Boolean,
        aiKeys: AiKeysModel?,
    ): ThemeDetailModel? {
        val theme = themeDatabase.getTheme(id = id)?.let(themeMapper::mapToThemeDetail) ?: return null

        val finalThemeContent = if (recreate) {
            val aiRepository: AiRepository = getKoin().get(
                parameters = { parametersOf(aiKeys) }
            )
            val result = aiRepository.generateCompletion(theme = theme)

            saveThemeContent(id = id, content = result)

            result
        } else {
            getCachedThemeContent(id = id)
        }

        return theme.copy(content = finalThemeContent)
    }

    private suspend fun saveThemeContent(id: Int, content: String) {
        if (cachedThemeContentLocalToggle.isEnabled()) {
            try {
                themeDatabase.saveThemeContent(id = id, content = content)
            } catch (expected: Throwable) {
                logger.error(expected)
            }
        }
    }

    private suspend fun getCachedThemeContent(id: Int): String {
        return if (cachedThemeContentLocalToggle.isEnabled()) {
            themeDatabase.getThemeContent(id = id).orEmpty()
        } else {
            ""
        }
    }
}
