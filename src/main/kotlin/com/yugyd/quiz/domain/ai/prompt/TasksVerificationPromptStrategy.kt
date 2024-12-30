package com.yugyd.quiz.domain.ai.prompt

import com.yugyd.quiz.domain.tasks.models.verification.TasksVerificationRequestModel

internal interface TasksVerificationPromptStrategy {
    fun getPrompt(tasksVerification: TasksVerificationRequestModel): String
}
