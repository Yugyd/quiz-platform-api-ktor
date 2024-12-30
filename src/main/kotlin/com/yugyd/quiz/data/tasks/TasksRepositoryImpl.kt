package com.yugyd.quiz.data.tasks

import com.yugyd.quiz.core.logger.Logger
import com.yugyd.quiz.core.toggles.CachedTasksLocalToggle
import com.yugyd.quiz.data.tasks.db.TasksDatabase
import com.yugyd.quiz.data.tasks.mapper.AiResponseToTasksMapper
import com.yugyd.quiz.domain.ai.AiRepository
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.tasks.models.list.TaskListModel
import com.yugyd.quiz.domain.tasks.models.verification.TaskVerificationModel
import com.yugyd.quiz.domain.tasks.models.verification.TasksVerificationRequestModel
import com.yugyd.quiz.domain.tasks.service.TasksRepository
import com.yugyd.quiz.domain.theme.service.ThemesRepository
import kotlinx.serialization.SerializationException
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

internal class TasksRepositoryImpl(
    private val tasksDatabase: TasksDatabase,
    private val logger: Logger,
    private val themeRepository: ThemesRepository,
    private val aiResponseToTasksMapper: AiResponseToTasksMapper,
    private val cachedTasksLocalToggle: CachedTasksLocalToggle,
) : TasksRepository {

    override suspend fun getTasks(
        content: ContentModel,
        themeId: Int,
        aiKeys: AiKeysModel,
    ): List<TaskListModel> {
        val theme = themeRepository.getThemeDetail(
            id = themeId,
            aiKeys = aiKeys,
        ) ?: return emptyList()

        val aiRepository: AiRepository = getKoin().get(
            parameters = { parametersOf(aiKeys) }
        )
        val finalTasks: List<TaskListModel> = try {
            val aiJson = aiRepository.generateTasks(theme = theme)

            logger.debug(TAG, aiJson)

            val result = aiResponseToTasksMapper.mapToTaskLists(aiJson)

            saveTasksModels(result)

            result
        } catch (error: SerializationException) {
            logger.debug(error)
            getCachedTaskModels(themeId = themeId)
        } catch (error: IllegalArgumentException) {
            logger.debug(error)
            getCachedTaskModels(themeId = themeId)
        }

        return finalTasks
    }

    override suspend fun verifyTask(
        content: ContentModel,
        requestModel: TasksVerificationRequestModel,
        aiKeys: AiKeysModel
    ): TaskVerificationModel {
        val aiRepository: AiRepository = getKoin().get(
            parameters = { parametersOf(aiKeys) }
        )
        val taskVerificationModel: TaskVerificationModel = try {
            val aiJson = aiRepository.verifyTask(requestModel)

            logger.debug(TAG, aiJson)

            aiResponseToTasksMapper.mapToTaskVerification(aiJson)
        } catch (error: SerializationException) {
            logger.debug(error)
            error("Serialization error")
        } catch (error: IllegalArgumentException) {
            logger.debug(error)
            error("Invalid argument error")
        }

        return taskVerificationModel
    }

    private suspend fun saveTasksModels(models: List<TaskListModel>) {
        if (cachedTasksLocalToggle.isEnabled()) {
            try {
                tasksDatabase.addTasks(models)
            } catch (expected: Throwable) {
                logger.error(expected)
            }
        }
    }

    private suspend fun getCachedTaskModels(themeId: Int): List<TaskListModel> {
        return if (cachedTasksLocalToggle.isEnabled()) {
            tasksDatabase.getTasks(themeId = themeId, limit = DEFAULT_TASKS_LIMIT)
        } else {
            emptyList()
        }
    }

    private companion object {
        private const val TAG = "TasksRepositoryImpl"
        private const val DEFAULT_TASKS_LIMIT = 10
    }
}
