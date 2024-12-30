package com.yugyd.quiz.data.tasks.mapper

import com.yugyd.quiz.core.logger.Logger
import com.yugyd.quiz.domain.tasks.models.list.TaskListModel
import com.yugyd.quiz.domain.tasks.models.verification.TaskVerificationModel
import kotlinx.serialization.json.Json

internal class AiResponseToTasksMapperImpl(
    private val json: Json,
    private val logger: Logger,
) : AiResponseToTasksMapper {

    override fun mapToTaskLists(aiResponseJson: String): List<TaskListModel> {
        val startTasksBlockIndex = aiResponseJson.indexOf("[")
        val endTasksBlockIndex = aiResponseJson.lastIndexOf("]")

        require(startTasksBlockIndex == -1 || endTasksBlockIndex == -1) {
            "Tasks block not found in response"
        }

        val tasksJson = aiResponseJson.substring(
            startTasksBlockIndex,
            endTasksBlockIndex + 1,
        )

        logger.debug(TAG, tasksJson)

        return json.decodeFromString<List<TaskListModel>>(tasksJson)
    }

    override fun mapToTaskVerification(aiResponseJson: String): TaskVerificationModel {
        val startTaskVerificationBlockIndex = aiResponseJson.indexOf("{")
        val endTaskVerificationBlockIndex = aiResponseJson.lastIndexOf("}")

        require(startTaskVerificationBlockIndex == -1 || endTaskVerificationBlockIndex == -1) {
            "Task verification block not found in response"
        }

        val taskVerificationJson = aiResponseJson.substring(
            startTaskVerificationBlockIndex,
            endTaskVerificationBlockIndex + 1,
        )

        logger.debug(TAG, taskVerificationJson)

        return Json.decodeFromString<TaskVerificationModel>(taskVerificationJson)
    }

    private companion object {
        private const val TAG = "ResponseToJsonTasksMapperImpl"
    }
}
