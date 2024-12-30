package com.yugyd.quiz.domain.tasks.di

import com.yugyd.quiz.domain.tasks.service.TasksService
import com.yugyd.quiz.domain.tasks.service.TasksServiceImpl
import org.koin.dsl.module

internal val tasksDomainModule = module {
    single<TasksService> {
        TasksServiceImpl(
            tasksRepository = get(),
        )
    }
}
