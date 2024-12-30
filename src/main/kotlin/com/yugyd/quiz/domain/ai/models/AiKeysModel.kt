package com.yugyd.quiz.domain.ai.models

import com.yugyd.quiz.domain.ai.AiProvider

internal data class AiKeysModel(
    val apiKey: String,
    val apiFolder: String?,
    val provider: AiProvider,
)
