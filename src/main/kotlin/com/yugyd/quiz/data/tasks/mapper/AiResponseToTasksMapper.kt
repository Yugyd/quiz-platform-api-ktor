package com.yugyd.quiz.data.tasks.mapper

import com.yugyd.quiz.domain.tasks.models.list.TaskListModel
import com.yugyd.quiz.domain.tasks.models.verification.TaskVerificationModel
import kotlinx.serialization.SerializationException

internal interface AiResponseToTasksMapper {

    /**
     * @throws SerializationException if serialization fails
     * @throws IllegalArgumentException if serialization fails or if answer is not found tasks block in response
     */
    fun mapToTaskLists(aiResponseJson: String): List<TaskListModel>

    /**
     * @throws SerializationException if serialization fails
     * @throws IllegalArgumentException if serialization fails or if answer is not found task verification block in
     * response
     */
    fun mapToTaskVerification(aiResponseJson: String): TaskVerificationModel
}
