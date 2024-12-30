package com.yugyd.quiz.domain.theme.service

import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel
import com.yugyd.quiz.domain.theme.models.list.ThemeListModel

internal interface ThemeService {
    suspend fun getThemes(content: ContentModel): List<ThemeListModel>
    suspend fun getThemes(content: ContentModel, parentThemeId: Int): List<ThemeListModel>

    @Throws(InvalidAiCredentialException::class)
    suspend fun getThemeDetail(
        id: Int,
        recreate: Boolean = false,
        aiKeys: AiKeysModel? = null,
    ): ThemeDetailModel?
}
