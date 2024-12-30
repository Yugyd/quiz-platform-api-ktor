package com.yugyd.quiz.data.ai.yandex.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class StatusDao {
    @SerialName("ALTERNATIVE_STATUS_UNSPECIFIED")
    ALTERNATIVE_STATUS_UNSPECIFIED,

    @SerialName("ALTERNATIVE_STATUS_TRUNCATED_FINAL")
    ALTERNATIVE_STATUS_TRUNCATED_FINAL,

    @SerialName("ALTERNATIVE_STATUS_FINAL")
    ALTERNATIVE_STATUS_FINAL,

    @SerialName("ALTERNATIVE_STATUS_CONTENT_FILTER")
    ALTERNATIVE_STATUS_CONTENT_FILTER,
}
