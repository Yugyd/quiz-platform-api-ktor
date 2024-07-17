package com.yugyd.data.themes

import com.yugyd.data.themes.db.ThemeDatabase
import com.yugyd.data.themes.mapper.ThemeMapper
import org.koin.dsl.module

internal val themesDataModule = module {
    factory<ThemeMapper> {
        ThemeMapper()
    }

    single<ThemeDatabase> {
        ThemeDatabase(
            database = get(),
            themeMapper = get(),
            dispatchersProvider = get(),
        )
    }

    single<ThemesRepository> {
        ThemesRepositoryImpl(
            themeDatabase = get(),
            databaseLocalToggle = get(),
        )
    }
}
