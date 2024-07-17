package com.yugyd.core.toggles

import org.koin.dsl.module

internal val togglesCoreModule = module {
    factory<DatabaseLocalToggle> {
        DatabaseLocalToggle()
    }
}
