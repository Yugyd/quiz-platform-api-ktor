package com.yugyd.data.themes

import com.yugyd.core.toggles.DatabaseLocalToggle
import com.yugyd.data.themes.db.ThemeDatabase
import com.yugyd.domain.ai.AiRepository
import com.yugyd.domain.ai.models.AiKeysModel
import com.yugyd.domain.content.models.ContentModel
import com.yugyd.domain.theme.models.ThemeModel
import com.yugyd.domain.theme.models.details.ThemeDetailModel
import com.yugyd.domain.theme.models.list.ThemeListModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

internal class ThemesRepositoryImpl(
    private val themeDatabase: ThemeDatabase,
    private val databaseLocalToggle: DatabaseLocalToggle,
) : ThemesRepository {

    private val themes = listOf(
        ThemeModel(
            id = 1,
            name = "East Slavic tribes and their neighbors.",
            description =
            "The settlement of the Slavs, their division into three branches - eastern, western and southern",
            alternativeDescription = "",
            iconUrl = null,
            content = null,
            childThemes = emptyList(),
            code = "1.1",
        ),
        ThemeModel(
            id = 2,
            name = "The emergence of statehood among the Eastern Slavs",
            description =
            "Princes and squad. Veche orders. Acceptance of Christianity. Categories of the population. Russian Truth",
            alternativeDescription = "",
            iconUrl = null,
            content = null,
            childThemes = emptyList(),
            code = "1.2",
        ),
    )

    override suspend fun getThemes(
        content: ContentModel,
    ): List<ThemeListModel> {
        if (!databaseLocalToggle.isEnabled()) {
            return themes.map(::toListModel)
        }

        return themeDatabase.getThemes().map(::toListModel)
    }

    override suspend fun getThemes(
        content: ContentModel,
        parentThemeId: Int,
    ): List<ThemeListModel> {
        if (!databaseLocalToggle.isEnabled()) {
            return themes.map(::toListModel)
        }

        return themeDatabase.getThemes(parentId = parentThemeId).map(::toListModel)
    }

    override suspend fun getThemeDetail(
        id: Int,
        recreate: Boolean,
        aiKeys: AiKeysModel?,
    ): ThemeDetailModel {
        val theme = if (!databaseLocalToggle.isEnabled()) {
            themes.find { it.id == id }!!.let(::toThemeDetail)
        } else {
            themeDatabase.getTheme(id = id)!!.let(::toThemeDetail)
        }

        val aiRepository: AiRepository = getKoin().get(
            parameters = { parametersOf(aiKeys) }
        )
        val finalThemeContent = if (recreate) {
            aiRepository.generateCompletion(theme = theme)
        } else {
            theme.content
        }

        return theme.copy(content = finalThemeContent)
    }

    private fun toListModel(model: ThemeModel): ThemeListModel {
        return ThemeListModel(
            id = model.id,
            name = model.name,
            description = model.description.orEmpty(),
            iconUrl = model.iconUrl,
            detail = model.childThemes.isNullOrEmpty(),
        )
    }

    private fun toThemeDetail(model: ThemeModel): ThemeDetailModel {
        return ThemeDetailModel(
            id = model.id,
            name = model.name,
            description = model.description.orEmpty(),
            iconUrl = model.iconUrl,
            detail = model.childThemes.isNullOrEmpty(),
            content = model.content.orEmpty(),
        )
    }
}
