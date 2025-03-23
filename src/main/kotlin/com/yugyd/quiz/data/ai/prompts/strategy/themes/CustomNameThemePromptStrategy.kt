package com.yugyd.quiz.data.ai.prompts.strategy.themes

import com.yugyd.quiz.core.coroutines.runCatchingWithoutCancellation
import com.yugyd.quiz.core.logger.Logger
import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal class CustomNameThemePromptStrategy(
    private val themePromptFileConfig: ThemePromptFileConfig,
    private val fallbackPromptStrategy: ThemePromptStrategy,
    private val logger: Logger,
) : ThemePromptStrategy {

    override suspend fun getPrompt(theme: ThemeDetailModel): String {
        val prompt = runCatchingWithoutCancellation {
            val prompt = themePromptFileConfig.getPrompt()
            String.format(prompt, theme.name)
        }
            .getOrElse {
                logger.debug(it)

                fallbackPromptStrategy.getPrompt(theme)
            }

        return prompt.trimIndent()
    }
}
