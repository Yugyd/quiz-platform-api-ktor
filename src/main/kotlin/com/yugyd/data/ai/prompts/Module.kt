package com.yugyd.data.ai.prompts

import com.yugyd.data.ai.prompts.factory.PromptFactory
import com.yugyd.data.ai.prompts.factory.PromptFactoryImpl
import com.yugyd.data.ai.prompts.models.PromptTypeModel
import org.koin.dsl.module

internal val promptModule = module {
    factory<PromptFactory> {
        PromptFactoryImpl()
    }

    factory { (promptType: PromptTypeModel) ->
        get<PromptFactory>().create(promptType)
    }
}
