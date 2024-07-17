package com.yugyd.domain.ai

import com.yugyd.domain.theme.models.details.ThemeDetailModel

internal interface AiRepository {
    suspend fun generateCompletion(theme: ThemeDetailModel): String
}
