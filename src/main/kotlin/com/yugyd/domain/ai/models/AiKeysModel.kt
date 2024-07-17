package com.yugyd.domain.ai.models

import com.yugyd.domain.ai.AiProvider

internal data class AiKeysModel(
    val apiKey: String,
    val apiFolder: String?,
    val provider: AiProvider,
)
