package com.yugyd.quiz.domain.theme.di

import com.yugyd.quiz.domain.theme.service.ThemeService
import com.yugyd.quiz.domain.theme.service.ThemeServiceImpl
import org.koin.dsl.module

internal val themesDomainModule = module {
    single<ThemeService> {
        ThemeServiceImpl(
            themesRepository = get(),
        )
    }
}
