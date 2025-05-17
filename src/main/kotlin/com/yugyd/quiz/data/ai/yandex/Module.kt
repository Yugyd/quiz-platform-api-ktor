package com.yugyd.quiz.data.ai.yandex

import com.yugyd.quiz.data.ai.AiClientFactory
import com.yugyd.quiz.data.ai.YANDEX_AI_QUALIFIER
import com.yugyd.quiz.data.ai.yandex.factory.YandexGptClientFactoryImpl
import org.koin.dsl.module

internal val yandexGptModule = module {
    factory<AiClientFactory>(YANDEX_AI_QUALIFIER) {
        YandexGptClientFactoryImpl(
            json = get(),
            aiHttpClientConfig = getOrNull(),
        )
    }
}
