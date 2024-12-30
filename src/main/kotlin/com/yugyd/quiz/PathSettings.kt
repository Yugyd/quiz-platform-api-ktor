package com.yugyd.quiz

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.IgnoreTrailingSlash

internal fun Application.configurePathSettings() {
    install(IgnoreTrailingSlash)
}
