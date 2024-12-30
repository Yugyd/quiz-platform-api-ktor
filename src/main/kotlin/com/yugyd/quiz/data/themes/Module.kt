package com.yugyd.quiz.data.themes

import com.yugyd.quiz.data.themes.db.ThemeDatabase
import com.yugyd.quiz.data.themes.mapper.ThemeEntityMapper
import com.yugyd.quiz.data.themes.mapper.ThemeMapper
import com.yugyd.quiz.domain.theme.service.ThemesRepository
import org.koin.dsl.module

internal val themesDataModule = module {
    factory<ThemeEntityMapper> {
        ThemeEntityMapper()
    }

    single<ThemeDatabase> {
        ThemeDatabase(
            database = get(),
            themeEntityMapper = get(),
            dispatchersProvider = get(),
        )
    }

    factory<ThemeMapper> {
        ThemeMapper()
    }

    single<ThemesRepository> {
        ThemesRepositoryImpl(
            themeDatabase = get(),
            themeMapper = get(),
            cachedThemeContentLocalToggle = get(),
            logger = get(),
        )
    }
}
