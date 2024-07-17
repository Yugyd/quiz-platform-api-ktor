package com.yugyd.app

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get

internal fun Application.configureSerialization() {
    val json = get<Json>()
    install(ContentNegotiation) {
        json(json)
    }
}
