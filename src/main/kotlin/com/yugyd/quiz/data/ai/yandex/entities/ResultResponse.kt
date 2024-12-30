package com.yugyd.quiz.data.ai.yandex.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ResultResponse(
    @SerialName("result") val result: CompletionDao,
)
