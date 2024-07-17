package com.yugyd.data.ai.prompts

import com.yugyd.domain.ai.prompt.PromptStrategy
import com.yugyd.domain.theme.models.details.ThemeDetailModel

internal class LitePromptStrategy : PromptStrategy {

    override fun getPrompt(theme: ThemeDetailModel): String {
        val template = theme.name
        return template.trim()
    }
}
