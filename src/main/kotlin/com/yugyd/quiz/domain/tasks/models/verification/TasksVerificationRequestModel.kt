package com.yugyd.quiz.domain.tasks.models.verification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TasksVerificationRequestModel(
    @SerialName("quest") val quest: String,
    @SerialName("userAnswer") val userAnswer: String,
    @SerialName("trueAnswer") val trueAnswer: String,
)
