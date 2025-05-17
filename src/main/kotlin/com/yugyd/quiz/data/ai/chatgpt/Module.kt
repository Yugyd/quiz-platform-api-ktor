package com.yugyd.quiz.data.ai.chatgpt

import com.yugyd.quiz.data.ai.AiClientFactory
import com.yugyd.quiz.data.ai.CHATGPT_AI_QUALIFIER
import com.yugyd.quiz.data.ai.chatgpt.factory.ChatGptClientFactoryImpl
import io.ktor.server.config.ApplicationConfig
import org.koin.dsl.module

internal fun chatGptModule(config: ApplicationConfig) = module {
    factory<AiClientFactory>(CHATGPT_AI_QUALIFIER) {
        val isLegacyChatGptCompletionsApi = config.property("customAi.useLegacyChatGptCompletionsApi")
            .getString()
            .toBoolean()

        ChatGptClientFactoryImpl(
            json = get(),
            aiHttpClientConfig = getOrNull(),
            isLegacyChatGptCompletionsApi = isLegacyChatGptCompletionsApi,
        )
    }
}
