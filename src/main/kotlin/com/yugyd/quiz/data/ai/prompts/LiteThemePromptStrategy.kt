package com.yugyd.quiz.data.ai.prompts

import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal class LiteThemePromptStrategy : ThemePromptStrategy {

    override fun getPrompt(theme: ThemeDetailModel): String {
        val template = theme.name
        return template.trim()
    }
}
