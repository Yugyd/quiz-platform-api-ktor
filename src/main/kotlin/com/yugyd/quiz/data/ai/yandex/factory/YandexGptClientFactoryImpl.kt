package com.yugyd.quiz.data.ai.yandex.factory

import com.yugyd.quiz.data.ai.AiClient
import com.yugyd.quiz.data.ai.AiClientFactory
import com.yugyd.quiz.data.ai.core.AiHttpClientConfig
import com.yugyd.quiz.data.ai.yandex.YandexGptClientImpl
import com.yugyd.quiz.data.ai.yandex.config.YandexAiKeys
import com.yugyd.quiz.data.themes.AiInitialException
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import kotlinx.serialization.json.Json

internal class YandexGptClientFactoryImpl(
    private val json: Json,
    private val aiHttpClientConfig: AiHttpClientConfig?,
) : AiClientFactory {

    override fun create(aiKeys: AiKeysModel?): AiClient {
        if (aiKeys == null) {
            throw AiInitialException("AiKeys is null")
        }

        if (aiKeys.apiFolder == null) {
            throw AiInitialException("YandexAiKeys.apiFolder is null")
        }

        val yandexAiKeys = YandexAiKeys(
            apiKey = aiKeys.apiKey,
            apiFolder = aiKeys.apiFolder,
            aiModel = aiKeys.aiModel,
        )
        return YandexGptClientImpl(
            yandexAiKeys = yandexAiKeys,
            aiConfig = aiHttpClientConfig,
            json = json,
        )
    }
}
