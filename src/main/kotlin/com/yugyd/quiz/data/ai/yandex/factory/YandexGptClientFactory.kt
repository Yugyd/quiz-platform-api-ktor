package com.yugyd.quiz.data.ai.yandex.factory

import com.yugyd.quiz.data.ai.AiClient
import com.yugyd.quiz.domain.ai.models.AiKeysModel

internal interface YandexGptClientFactory {
    fun create(aiKeys: AiKeysModel?): AiClient
}
