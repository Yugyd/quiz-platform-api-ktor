package com.yugyd.quiz.data.ai.chatgpt.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("output_text")
internal data class OutputTextContentItemDto(
    val type: String,
    val text: String,
    val annotations: List<String> = emptyList()
) : ContentItemDto
