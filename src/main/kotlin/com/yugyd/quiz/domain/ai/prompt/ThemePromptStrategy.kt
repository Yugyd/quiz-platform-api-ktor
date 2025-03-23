package com.yugyd.quiz.domain.ai.prompt

import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal interface ThemePromptStrategy {
    suspend fun getPrompt(theme: ThemeDetailModel): String
}
