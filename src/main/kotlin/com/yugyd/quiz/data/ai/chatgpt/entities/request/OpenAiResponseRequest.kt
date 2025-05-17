package com.yugyd.quiz.data.ai.chatgpt.entities.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OpenAiResponseRequest(
    val model: String,
    val input: String,
    @SerialName("max_output_tokens") val maxTokens: Int,
    val temperature: Double,
)
