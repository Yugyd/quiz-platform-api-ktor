package com.yugyd.quiz.data.ai.chatgpt.entities

import kotlinx.serialization.Serializable

@Serializable
internal data class OpenAiResponse(
    val output: List<OpenAiResponseItemDto>,
)
