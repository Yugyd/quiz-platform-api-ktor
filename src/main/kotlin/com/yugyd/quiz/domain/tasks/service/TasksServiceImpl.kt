package com.yugyd.quiz.domain.tasks.service

import com.yugyd.quiz.domain.ai.models.AiKeysModel
import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.tasks.models.list.TaskListModel
import com.yugyd.quiz.domain.tasks.models.verification.TaskVerificationModel
import com.yugyd.quiz.domain.tasks.models.verification.TasksVerificationRequestModel

internal class TasksServiceImpl(
    private val tasksRepository: TasksRepository,
) : TasksService {

    override suspend fun getTasks(
        content: ContentModel,
        themeId: Int,
        aiKeys: AiKeysModel,
    ): List<TaskListModel> {
        return tasksRepository.getTasks(
            content = content,
            themeId = themeId,
            aiKeys = aiKeys,
        )
    }

    override suspend fun verifyTask(
        content: ContentModel,
        requestModel: TasksVerificationRequestModel,
        aiKeys: AiKeysModel,
    ): TaskVerificationModel {
        return tasksRepository.verifyTask(
            content = content,
            requestModel = requestModel,
            aiKeys = aiKeys,
        )
    }
}
