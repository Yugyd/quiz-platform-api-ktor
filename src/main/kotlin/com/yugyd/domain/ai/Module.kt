package com.yugyd.domain.ai

import com.yugyd.domain.ai.factory.AiKeysFactory
import com.yugyd.domain.ai.factory.AiKeysFactoryImpl
import org.koin.dsl.module

internal val aiDomainModule = module {
    factory<AiKeysFactory> {
        AiKeysFactoryImpl()
    }
}
