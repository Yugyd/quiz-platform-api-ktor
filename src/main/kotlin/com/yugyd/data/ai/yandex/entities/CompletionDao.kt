package com.yugyd.data.ai.yandex.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CompletionDao(
    @SerialName("alternatives") val alternatives: List<AlternativeDao>,
    @SerialName("usage") val usage: UsageDao,
    @SerialName("modelVersion") val modelVersion: String,
)
