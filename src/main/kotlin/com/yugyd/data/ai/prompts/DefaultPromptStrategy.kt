package com.yugyd.data.ai.prompts

import com.yugyd.domain.ai.prompt.PromptStrategy
import com.yugyd.domain.theme.models.details.ThemeDetailModel

internal class DefaultPromptStrategy : PromptStrategy {

    override fun getPrompt(theme: ThemeDetailModel): String {
        val template = """
        Theme:

        ${theme.name}

        Reveal the moments::

        ${theme.description}
        """

        return template.trimIndent()
    }
}
