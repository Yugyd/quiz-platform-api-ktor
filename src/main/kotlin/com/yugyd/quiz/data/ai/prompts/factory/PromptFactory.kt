package com.yugyd.quiz.data.ai.prompts.factory

import com.yugyd.quiz.data.ai.prompts.models.PromptTypeModel
import com.yugyd.quiz.domain.ai.prompt.TasksPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.TasksVerificationPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy

internal interface PromptFactory {

    fun createThemePromptStrategy(typeModel: PromptTypeModel): ThemePromptStrategy

    fun createTasksPromptStrategy(typeModel: PromptTypeModel): TasksPromptStrategy

    fun createTasksVerificationPromptStrategy(typeModel: PromptTypeModel): TasksVerificationPromptStrategy

}
