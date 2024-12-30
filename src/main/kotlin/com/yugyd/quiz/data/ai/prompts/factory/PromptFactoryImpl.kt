package com.yugyd.quiz.data.ai.prompts.factory

import com.yugyd.quiz.data.ai.prompts.DefaultTasksPromptStrategy
import com.yugyd.quiz.data.ai.prompts.DefaultTasksVerificationPromptStrategy
import com.yugyd.quiz.data.ai.prompts.DefaultThemePromptStrategy
import com.yugyd.quiz.data.ai.prompts.LiteThemePromptStrategy
import com.yugyd.quiz.data.ai.prompts.models.PromptTypeModel

internal class PromptFactoryImpl : PromptFactory {

    override fun createThemePromptStrategy(typeModel: PromptTypeModel) = when (typeModel) {
        PromptTypeModel.DEFAULT -> DefaultThemePromptStrategy()
        PromptTypeModel.LITE -> LiteThemePromptStrategy()
    }

    override fun createTasksPromptStrategy(typeModel: PromptTypeModel) = when (typeModel) {
        PromptTypeModel.DEFAULT, PromptTypeModel.LITE -> DefaultTasksPromptStrategy()
    }

    override fun createTasksVerificationPromptStrategy(typeModel: PromptTypeModel) = when (typeModel) {
        PromptTypeModel.DEFAULT, PromptTypeModel.LITE -> DefaultTasksVerificationPromptStrategy()
    }
}
