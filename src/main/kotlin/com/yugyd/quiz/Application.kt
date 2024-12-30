package com.yugyd.quiz

import com.yugyd.quiz.routes.tasks.configureTasksRouting
import com.yugyd.quiz.routes.themes.configureThemesRouting
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("Unused")
fun Application.module() {
    configureDi()
    configureDatabase()
    configureServer()
    configureRouting()
}

internal fun Application.configureServer() {
    configureSerialization()
    configureErrorHandling()
    configureDefaultHeaders()
    configurePathSettings()
    configureLogging()
    configureMonitoring()
    configureRateLimit()
}

internal fun Application.configureRouting() {
    configureThemesRouting()
    configureTasksRouting()
}
