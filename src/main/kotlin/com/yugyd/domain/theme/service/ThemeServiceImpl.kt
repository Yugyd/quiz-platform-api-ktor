package com.yugyd.domain.theme.service

import com.yugyd.data.themes.ThemesRepository
import com.yugyd.domain.ai.models.AiKeysModel
import com.yugyd.domain.content.models.ContentModel
import com.yugyd.domain.theme.models.details.ThemeDetailModel
import com.yugyd.domain.theme.models.list.ThemeListModel

internal class ThemeServiceImpl(
    private val themesRepository: ThemesRepository,
) : ThemeService {

    override suspend fun getThemes(content: ContentModel): List<ThemeListModel> {
        return themesRepository.getThemes(content)
    }

    override suspend fun getThemes(content: ContentModel, parentThemeId: Int): List<ThemeListModel> {
        return themesRepository.getThemes(content, parentThemeId)
    }

    override suspend fun getThemeDetail(id: Int, recreate: Boolean, aiKeys: AiKeysModel?): ThemeDetailModel? {
        return themesRepository.getThemeDetail(
            id = id,
            recreate = recreate,
            aiKeys = aiKeys,
        )
    }
}
