package com.yugyd.data.ai.yandex.entities.request

import kotlinx.serialization.Serializable

@Serializable
internal data class CompletionOptionsDao(
    val stream: Boolean,
    val temperature: Double,
    val maxTokens: Int,
)
