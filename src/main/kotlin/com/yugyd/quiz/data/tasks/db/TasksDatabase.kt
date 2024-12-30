package com.yugyd.quiz.data.tasks.db

import com.yugyd.quiz.core.coroutines.DispatchersProvider
import com.yugyd.quiz.data.tasks.db.entities.TasksDAO
import com.yugyd.quiz.data.tasks.db.tables.TasksTable
import com.yugyd.quiz.data.tasks.mapper.TaskEntityMapper
import com.yugyd.quiz.data.themes.db.entities.ThemeDAO
import com.yugyd.quiz.domain.tasks.models.list.TaskListModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

internal class TasksDatabase(
    private val database: Database,
    private val entityMapper: TaskEntityMapper,
    private val dispatchersProvider: DispatchersProvider,
) {
    fun initDatabase() {
        transaction(database) {
            SchemaUtils.create(TasksTable)
        }
    }

    suspend fun getTasks(themeId: Int, limit: Int): List<TaskListModel> {
        return newSuspendedTransaction(dispatchersProvider.io) {
            TasksDAO
                .find {
                    TasksTable.category eq themeId
                }
                .limit(limit)
                .map(entityMapper::map)
        }
    }

    suspend fun addTasks(models: List<TaskListModel>) = newSuspendedTransaction(dispatchersProvider.io) {
        models.forEach { model ->
            val themeId = ThemeDAO.findById(model.category)?.id ?: throw IllegalArgumentException("Theme not founded")

            TasksDAO.new {
                quest = model.quest
                image = model.image
                trueAnswer = model.trueAnswer
                answer2 = model.answer2
                answer3 = model.answer3
                answer4 = model.answer4
                answer5 = model.answer5
                answer6 = model.answer6
                answer7 = model.answer7
                answer8 = model.answer8
                complexity = model.complexity
                category = themeId
                section = model.section
                type = model.type
            }
        }
    }
}
