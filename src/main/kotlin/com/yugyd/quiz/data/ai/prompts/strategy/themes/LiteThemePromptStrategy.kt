package com.yugyd.quiz.data.ai.prompts.strategy.themes

import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal class LiteThemePromptStrategy : ThemePromptStrategy {

    override suspend fun getPrompt(theme: ThemeDetailModel): String {
        val template = "Talk about \"${theme.name}\""
        return template.trim()
    }
}
