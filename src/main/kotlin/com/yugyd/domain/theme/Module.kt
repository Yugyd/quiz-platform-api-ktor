package com.yugyd.domain.theme

import com.yugyd.domain.theme.service.ThemeService
import com.yugyd.domain.theme.service.ThemeServiceImpl
import org.koin.dsl.module

internal val themesDomainModule = module {
    single<ThemeService> {
        ThemeServiceImpl(
            themesRepository = get(),
        )
    }
}
