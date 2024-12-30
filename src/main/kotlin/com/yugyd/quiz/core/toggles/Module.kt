package com.yugyd.quiz.core.toggles

import org.koin.dsl.module

internal val togglesCoreModule = module {
    factory<CachedTasksLocalToggle> {
        CachedTasksLocalToggle()
    }

    factory<CachedThemeContentLocalToggle> {
        CachedThemeContentLocalToggle()
    }
}
