package com.yugyd.quiz.data.ai.prompts

import com.yugyd.quiz.data.ai.prompts.factory.PromptFactory
import com.yugyd.quiz.data.ai.prompts.factory.PromptFactoryImpl
import com.yugyd.quiz.data.ai.prompts.models.PromptTypeModel
import com.yugyd.quiz.domain.ai.prompt.TasksPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.TasksVerificationPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy
import org.koin.dsl.module

internal val promptModule = module {
    factory<PromptFactory> {
        PromptFactoryImpl()
    }

    factory<ThemePromptStrategy> { (promptType: PromptTypeModel) ->
        get<PromptFactory>().createThemePromptStrategy(promptType)
    }

    factory<TasksVerificationPromptStrategy> { (promptType: PromptTypeModel) ->
        get<PromptFactory>().createTasksVerificationPromptStrategy(promptType)
    }

    factory<TasksPromptStrategy> { (promptType: PromptTypeModel) ->
        get<PromptFactory>().createTasksPromptStrategy(promptType)
    }
}
