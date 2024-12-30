package com.yugyd.quiz.core.coroutines

import org.koin.dsl.module

internal val coroutinesCoreModule = module {
    single<DispatchersProvider> {
        DispatchersProviderImpl()
    }
}
