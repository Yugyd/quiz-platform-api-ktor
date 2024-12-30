package com.yugyd.quiz.data.tasks

import com.yugyd.quiz.data.tasks.db.TasksDatabase
import com.yugyd.quiz.data.tasks.mapper.AiResponseToTasksMapper
import com.yugyd.quiz.data.tasks.mapper.AiResponseToTasksMapperImpl
import com.yugyd.quiz.data.tasks.mapper.TaskEntityMapper
import com.yugyd.quiz.domain.tasks.service.TasksRepository
import org.koin.dsl.module

internal val tasksDataModule = module {
    factory<AiResponseToTasksMapper> {
        AiResponseToTasksMapperImpl(
            logger = get(),
            json = get(),
        )
    }

    factory<TaskEntityMapper> {
        TaskEntityMapper()
    }

    single<TasksDatabase> {
        TasksDatabase(
            database = get(),
            entityMapper = get(),
            dispatchersProvider = get(),
        )
    }

    single<TasksRepository> {
        TasksRepositoryImpl(
            tasksDatabase = get(),
            logger = get(),
            themeRepository = get(),
            aiResponseToTasksMapper = get(),
            cachedTasksLocalToggle = get(),
        )
    }
}
