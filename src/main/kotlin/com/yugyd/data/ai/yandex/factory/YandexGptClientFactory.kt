package com.yugyd.data.ai.yandex.factory

import com.yugyd.data.ai.AiClient
import com.yugyd.domain.ai.models.AiKeysModel

internal interface YandexGptClientFactory {
    fun create(aiKeys: AiKeysModel?): AiClient
}
