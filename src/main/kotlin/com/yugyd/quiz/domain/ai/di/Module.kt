package com.yugyd.quiz.domain.ai.di

import com.yugyd.quiz.domain.ai.factory.AiKeysFactory
import com.yugyd.quiz.domain.ai.factory.AiKeysFactoryImpl
import com.yugyd.quiz.domain.ai.mappers.AiHeaderMapper
import com.yugyd.quiz.domain.ai.mappers.AiHeaderMapperImpl
import org.koin.dsl.module

internal val aiDomainModule = module {
    factory<AiKeysFactory> {
        AiKeysFactoryImpl()
    }

    factory<AiHeaderMapper> { AiHeaderMapperImpl() }
}
