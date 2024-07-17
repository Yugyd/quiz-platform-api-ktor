package com.yugyd.data.ai.prompts.factory

import com.yugyd.data.ai.prompts.models.PromptTypeModel
import com.yugyd.domain.ai.prompt.PromptStrategy

internal interface PromptFactory {
    fun create(typeModel: PromptTypeModel): PromptStrategy
}
