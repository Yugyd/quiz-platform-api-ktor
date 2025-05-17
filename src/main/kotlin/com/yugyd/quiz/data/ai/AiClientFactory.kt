package com.yugyd.quiz.data.ai

import com.yugyd.quiz.domain.ai.models.AiKeysModel

internal interface AiClientFactory {
    fun create(aiKeys: AiKeysModel?): AiClient
}
