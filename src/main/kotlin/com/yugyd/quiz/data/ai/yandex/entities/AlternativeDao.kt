package com.yugyd.quiz.data.ai.yandex.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AlternativeDao(
    @SerialName("message") val message: MessageDao,
    @SerialName("status") val status: StatusDao,
)
