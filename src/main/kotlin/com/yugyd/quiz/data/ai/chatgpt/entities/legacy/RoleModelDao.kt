package com.yugyd.quiz.data.ai.chatgpt.entities.legacy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class RoleModelDao {
    @SerialName("user")
    USER,

    @SerialName("assistant")
    ASSISTANT,
}
