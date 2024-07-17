package com.yugyd.core.database

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

internal val databaseCoreModule = module {
    single<Database> {
        Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            user = "root",
            driver = "org.h2.Driver",
            password = ""
        )
    }
}
