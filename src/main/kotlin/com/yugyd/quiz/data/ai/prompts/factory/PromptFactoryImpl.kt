package com.yugyd.quiz.data.ai.prompts.factory

import com.yugyd.quiz.core.logger.Logger
import com.yugyd.quiz.data.ai.prompts.models.TaskPromptTypeModel
import com.yugyd.quiz.data.ai.prompts.models.ThemePromptTypeModel
import com.yugyd.quiz.data.ai.prompts.strategy.tasks.CustomNameAndContentTasksPromptStrategy
import com.yugyd.quiz.data.ai.prompts.strategy.tasks.DefaultTasksPromptStrategy
import com.yugyd.quiz.data.ai.prompts.strategy.tasks.DefaultTasksVerificationPromptStrategy
import com.yugyd.quiz.data.ai.prompts.strategy.tasks.TasksPromptConfig
import com.yugyd.quiz.data.ai.prompts.strategy.tasks.TaskPromptFileConfig
import com.yugyd.quiz.data.ai.prompts.strategy.themes.CustomNameThemePromptStrategy
import com.yugyd.quiz.data.ai.prompts.strategy.themes.CustomNameWithDescriptionThemePromptStrategy
import com.yugyd.quiz.data.ai.prompts.strategy.themes.DefaultThemePromptStrategy
import com.yugyd.quiz.data.ai.prompts.strategy.themes.LiteThemePromptStrategy
import com.yugyd.quiz.data.ai.prompts.strategy.themes.ThemePromptFileConfig

internal class PromptFactoryImpl(
    private val themePromptFileConfig: ThemePromptFileConfig,
    private val taskPromptFileConfig: TaskPromptFileConfig,
    private val logger: Logger,
) : PromptFactory {

    override fun createThemePromptStrategy(typeModel: ThemePromptTypeModel) = when (typeModel) {
        ThemePromptTypeModel.CUSTOM_NAME -> {
            CustomNameThemePromptStrategy(
                themePromptFileConfig = themePromptFileConfig,
                fallbackPromptStrategy = DefaultThemePromptStrategy(),
                logger = logger,
            )
        }

        ThemePromptTypeModel.CUSTOM_NAME_AND_DESCRIPTION -> {
            CustomNameWithDescriptionThemePromptStrategy(
                themePromptFileConfig = themePromptFileConfig,
                fallbackPromptStrategy = DefaultThemePromptStrategy(),
                logger = logger,
            )
        }

        ThemePromptTypeModel.DEFAULT -> DefaultThemePromptStrategy()
        ThemePromptTypeModel.LITE -> LiteThemePromptStrategy()
    }

    override fun createTasksPromptStrategy(typeModel: TaskPromptTypeModel) = when (typeModel) {
        TaskPromptTypeModel.DEFAULT, TaskPromptTypeModel.LITE -> {
            DefaultTasksPromptStrategy(TasksPromptConfig(tasksCount = DefaultTasksPromptStrategy.DEFAULT_TASKS_COUNT))
        }

        TaskPromptTypeModel.CUSTOM_NAME_AND_CONTENT -> {
            CustomNameAndContentTasksPromptStrategy(
                taskPromptFileConfig = taskPromptFileConfig,
                fallbackPromptStrategy = DefaultTasksPromptStrategy(TasksPromptConfig(tasksCount = DefaultTasksPromptStrategy.DEFAULT_TASKS_COUNT)),
                logger = logger,
            )
        }
    }

    override fun createTasksVerificationPromptStrategy(typeModel: TaskPromptTypeModel) = when (typeModel) {
        TaskPromptTypeModel.DEFAULT, TaskPromptTypeModel.LITE, TaskPromptTypeModel.CUSTOM_NAME_AND_CONTENT -> DefaultTasksVerificationPromptStrategy()
    }
}
