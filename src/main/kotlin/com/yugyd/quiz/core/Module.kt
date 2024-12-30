package com.yugyd.quiz.core

import com.yugyd.quiz.core.logger.Logger
import com.yugyd.quiz.core.logger.LoggerImpl
import io.ktor.server.application.ApplicationEnvironment
import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val serializationCoreModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}

internal fun loggerCoreModule(environment: ApplicationEnvironment) = module {
    single<Logger> {
        LoggerImpl(environment.log)
    }
}
