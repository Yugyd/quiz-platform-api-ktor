package com.yugyd.data.ai.prompts.factory

import com.yugyd.data.ai.prompts.DefaultPromptStrategy
import com.yugyd.data.ai.prompts.LitePromptStrategy
import com.yugyd.data.ai.prompts.models.PromptTypeModel

internal class PromptFactoryImpl : PromptFactory {

    override fun create(typeModel: PromptTypeModel) = when (typeModel) {
        PromptTypeModel.DEFAULT -> DefaultPromptStrategy()
        PromptTypeModel.LITE -> LitePromptStrategy()
    }
}
