package com.yugyd.domain.ai.prompt

import com.yugyd.domain.theme.models.details.ThemeDetailModel

internal interface PromptStrategy {
    fun getPrompt(theme: ThemeDetailModel): String
}
