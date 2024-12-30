package com.yugyd.quiz.data.ai.yandex.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UsageDao(
    @SerialName("inputTextTokens") val inputTextTokens: String,
    @SerialName("completionTokens") val completionTokens: String,
    @SerialName("totalTokens") val totalTokens: String,
)
