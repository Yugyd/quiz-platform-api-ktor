package com.yugyd.quiz.data.ai.prompts.strategy.tasks

import com.yugyd.quiz.core.coroutines.runCatchingWithoutCancellation
import com.yugyd.quiz.core.logger.Logger
import com.yugyd.quiz.domain.ai.prompt.TasksPromptStrategy
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal class CustomNameAndContentTasksPromptStrategy(
    private val taskPromptFileConfig: TaskPromptFileConfig,
    private val fallbackPromptStrategy: TasksPromptStrategy,
    private val logger: Logger,
) : TasksPromptStrategy {

    override suspend fun getPrompt(theme: ThemeDetailModel): String {
        val prompt = runCatchingWithoutCancellation {
            val prompt = taskPromptFileConfig.getPrompt()
            String.format(prompt, theme.name, theme.content)
        }
            .getOrElse {
                logger.debug(it)

                fallbackPromptStrategy.getPrompt(theme)
            }

        return prompt.trimIndent()
    }
}
