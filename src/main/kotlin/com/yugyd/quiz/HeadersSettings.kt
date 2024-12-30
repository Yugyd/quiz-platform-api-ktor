package com.yugyd.quiz

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.defaultheaders.DefaultHeaders

internal fun Application.configureDefaultHeaders() {
    install(DefaultHeaders)
}
