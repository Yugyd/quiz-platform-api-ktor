package com.yugyd.quiz.data.ai.chatgpt.entities.legacy.request

import com.yugyd.quiz.data.ai.chatgpt.entities.legacy.ChatMessageDto
import kotlinx.serialization.Serializable

@Serializable
internal data class ChatCompletionRequest(
    val model: String,
    val messages: List<ChatMessageDto>,
    val temperature: Double,
)
