package com.yugyd.quiz.data.ai.chatgpt.entities.legacy

import kotlinx.serialization.Serializable

@Serializable
internal data class ChatChoiceDto(
    val message: ChatMessageDto,
)
