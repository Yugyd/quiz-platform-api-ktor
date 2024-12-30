package com.yugyd.quiz.data.ai.yandex.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class RoleModelDao {
    @SerialName("user")
    USER,

    @SerialName("system")
    SYSTEM,

    @SerialName("assistant")
    ASSISTANT,
}
