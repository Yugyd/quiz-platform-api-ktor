package com.yugyd.quiz.data.ai.prompts

import com.yugyd.quiz.data.ai.prompts.factory.PromptFactory
import com.yugyd.quiz.data.ai.prompts.factory.PromptFactoryImpl
import com.yugyd.quiz.data.ai.prompts.models.TaskPromptTypeModel
import com.yugyd.quiz.data.ai.prompts.models.ThemePromptTypeModel
import com.yugyd.quiz.data.ai.prompts.strategy.tasks.TaskPromptFileConfig
import com.yugyd.quiz.data.ai.prompts.strategy.themes.ThemePromptFileConfig
import com.yugyd.quiz.data.fileconfig.FileConfig
import com.yugyd.quiz.data.fileconfig.FileConfigImpl
import com.yugyd.quiz.domain.ai.prompt.TasksPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.TasksVerificationPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy
import io.ktor.server.config.ApplicationConfig
import org.koin.dsl.module

internal fun promptModule(config: ApplicationConfig) = module {
    single<FileConfig> { FileConfigImpl() }

    val themeFilePath = config.property("customAi.themePromptFile").getString()
    single<ThemePromptFileConfig> { ThemePromptFileConfig(get(), themeFilePath) }

    val taskFilePath = config.property("customAi.taskPromptFile").getString()
    single<TaskPromptFileConfig> { TaskPromptFileConfig(get(), taskFilePath) }

    factory<PromptFactory> {
        PromptFactoryImpl(get(), get(), get())
    }

    factory<ThemePromptStrategy> { (promptType: ThemePromptTypeModel) ->
        get<PromptFactory>().createThemePromptStrategy(promptType)
    }

    factory<TasksVerificationPromptStrategy> { (promptType: TaskPromptTypeModel) ->
        get<PromptFactory>().createTasksVerificationPromptStrategy(promptType)
    }

    factory<TasksPromptStrategy> { (promptType: TaskPromptTypeModel) ->
        get<PromptFactory>().createTasksPromptStrategy(promptType)
    }
}
