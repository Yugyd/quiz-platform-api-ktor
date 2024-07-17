package com.yugyd.domain.theme.service

import com.yugyd.domain.ai.models.AiKeysModel
import com.yugyd.domain.content.models.ContentModel
import com.yugyd.domain.theme.models.details.ThemeDetailModel
import com.yugyd.domain.theme.models.list.ThemeListModel

internal interface ThemeService {
    suspend fun getThemes(content: ContentModel): List<ThemeListModel>
    suspend fun getThemes(content: ContentModel, parentThemeId: Int): List<ThemeListModel>
    suspend fun getThemeDetail(
        id: Int,
        recreate: Boolean = false,
        aiKeys: AiKeysModel? = null,
    ): ThemeDetailModel?
}

