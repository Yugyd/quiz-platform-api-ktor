package com.yugyd.data.ai.yandex

import com.yugyd.data.ai.yandex.factory.YandexGptClientFactory
import com.yugyd.data.ai.yandex.factory.YandexGptClientFactoryImpl
import org.koin.dsl.module

internal val yandexGptModule = module {
    single<YandexGptClientFactory> {
        YandexGptClientFactoryImpl(
            json = get(),
            aiHttpClientConfig = getOrNull(),
        )
    }
}
