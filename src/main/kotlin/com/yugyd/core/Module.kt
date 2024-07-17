package com.yugyd.core

import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val serializationCoreModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
}
