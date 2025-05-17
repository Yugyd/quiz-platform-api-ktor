package com.yugyd.quiz.data.ai.chatgpt.factory

import com.yugyd.quiz.data.ai.AiClient
import com.yugyd.quiz.data.ai.AiClientFactory
import com.yugyd.quiz.data.ai.chatgpt.ChatGptClientImpl
import com.yugyd.quiz.data.ai.chatgpt.LegacyChatGptClientImpl
import com.yugyd.quiz.data.ai.chatgpt.config.ChatGptAiKeys
import com.yugyd.quiz.data.ai.core.AiHttpClientConfig
import com.yugyd.quiz.data.themes.AiInitialException
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import kotlinx.serialization.json.Json

internal class ChatGptClientFactoryImpl(
    private val json: Json,
    private val aiHttpClientConfig: AiHttpClientConfig?,
    private val isLegacyChatGptCompletionsApi: Boolean = false,
) : AiClientFactory {

    override fun create(aiKeys: AiKeysModel?): AiClient {
        if (aiKeys == null) {
            throw AiInitialException("AiKeys is null")
        }

        val concreteAiKeys = ChatGptAiKeys(
            apiKey = aiKeys.apiKey,
            apiModel = aiKeys.aiModel,
        )
        return if (isLegacyChatGptCompletionsApi) {
            LegacyChatGptClientImpl(
                aiKeys = concreteAiKeys,
                aiConfig = aiHttpClientConfig,
                json = json,
            )
        } else {
            ChatGptClientImpl(
                aiKeys = concreteAiKeys,
                aiConfig = aiHttpClientConfig,
                json = json,
            )
        }
    }
}
