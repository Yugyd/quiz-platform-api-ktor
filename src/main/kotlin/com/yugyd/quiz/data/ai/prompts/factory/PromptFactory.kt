package com.yugyd.quiz.data.ai.prompts.factory

import com.yugyd.quiz.data.ai.prompts.models.TaskPromptTypeModel
import com.yugyd.quiz.data.ai.prompts.models.ThemePromptTypeModel
import com.yugyd.quiz.domain.ai.prompt.TasksPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.TasksVerificationPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy

internal interface PromptFactory {

    fun createThemePromptStrategy(typeModel: ThemePromptTypeModel): ThemePromptStrategy

    fun createTasksPromptStrategy(typeModel: TaskPromptTypeModel): TasksPromptStrategy

    fun createTasksVerificationPromptStrategy(typeModel: TaskPromptTypeModel): TasksVerificationPromptStrategy

}
