package com.yugyd.quiz.data.ai.chatgpt.entities

import kotlinx.serialization.Serializable

@Serializable
internal data class OpenAiResponseItemDto(
    val id: String,
    val type: String,
    val role: String,
    val content: List<ContentItemDto>,
)
