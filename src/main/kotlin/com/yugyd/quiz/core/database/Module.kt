package com.yugyd.quiz.core.database

import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun databaseCoreModule(config: ApplicationConfig): Module {
    return module {
        single<Database> {
            val dbUrl = config.property("storage.jdbcURL").getString()
            val dbUser = config.property("storage.user").getString()
            val dbPassword = config.property("storage.password").getString()
            val dbDriver = config.property("storage.driverClassName").getString()

            Database.connect(
                url = dbUrl,
                driver = dbDriver,
                user = dbUser,
                password = dbPassword,
            )
        }
    }
}
