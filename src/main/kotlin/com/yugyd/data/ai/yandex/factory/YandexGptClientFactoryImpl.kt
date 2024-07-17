package com.yugyd.data.ai.yandex.factory

import com.yugyd.data.ai.AiClient
import com.yugyd.data.ai.yandex.AiHttpClientConfig
import com.yugyd.data.ai.yandex.YandexGptClientImpl
import com.yugyd.data.ai.yandex.config.YandexAiKeys
import com.yugyd.data.themes.AiInitialException
import com.yugyd.domain.ai.models.AiKeysModel
import kotlinx.serialization.json.Json

internal class YandexGptClientFactoryImpl(
    private val json: Json,
    private val aiHttpClientConfig: AiHttpClientConfig?,
) : YandexGptClientFactory {

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
        )
        return YandexGptClientImpl(
            yandexAiKeys = yandexAiKeys,
            aiConfig = aiHttpClientConfig,
            json = json,
        )
    }
}
