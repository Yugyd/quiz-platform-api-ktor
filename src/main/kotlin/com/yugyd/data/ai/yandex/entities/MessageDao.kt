package com.yugyd.data.ai.yandex.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MessageDao(
    @SerialName("role") val role: RoleModelDao,
    @SerialName("text") val text: String,
)
