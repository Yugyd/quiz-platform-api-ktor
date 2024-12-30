package com.yugyd.quiz

import com.yugyd.quiz.data.tasks.db.TasksDatabase
import com.yugyd.quiz.data.themes.db.ThemeDatabase
import io.ktor.server.application.Application
import org.koin.ktor.ext.getKoin

internal fun Application.configureDatabase() {
    val themeDatabase = getKoin().get<ThemeDatabase>()
    val tasksDatabase = getKoin().get<TasksDatabase>()
    themeDatabase.initDatabase()
    tasksDatabase.initDatabase()
}
