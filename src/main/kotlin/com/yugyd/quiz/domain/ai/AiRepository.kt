package com.yugyd.quiz.domain.ai

import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException
import com.yugyd.quiz.domain.tasks.models.verification.TasksVerificationRequestModel
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal interface AiRepository {

    @Throws(InvalidAiCredentialException::class)
    suspend fun generateCompletion(theme: ThemeDetailModel): String

    @Throws(InvalidAiCredentialException::class)
    suspend fun generateTasks(theme: ThemeDetailModel): String

    @Throws(InvalidAiCredentialException::class)
    suspend fun verifyTask(requestModel: TasksVerificationRequestModel): String
}
