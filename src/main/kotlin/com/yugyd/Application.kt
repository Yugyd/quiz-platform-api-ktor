package com.yugyd

import com.yugyd.app.configureDi
import com.yugyd.app.configureErrorHandling
import com.yugyd.app.configureRouting
import com.yugyd.app.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
    )
        .start(wait = true)
}

fun Application.module() {
    configureDi()
    configureSerialization()
    configureErrorHandling()
    configureRouting()
}
