package com.yugyd.quiz.domain.tasks.service

import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.tasks.models.list.TaskListModel
import com.yugyd.quiz.domain.tasks.models.verification.TaskVerificationModel
import com.yugyd.quiz.domain.tasks.models.verification.TasksVerificationRequestModel

internal interface TasksService {

    @Throws(InvalidAiCredentialException::class)
    suspend fun getTasks(
        content: ContentModel,
        themeId: Int,
        aiKeys: AiKeysModel,
    ): List<TaskListModel>

    @Throws(InvalidAiCredentialException::class)
    suspend fun verifyTask(
        content: ContentModel,
        requestModel: TasksVerificationRequestModel,
        aiKeys: AiKeysModel,
    ): TaskVerificationModel
}
