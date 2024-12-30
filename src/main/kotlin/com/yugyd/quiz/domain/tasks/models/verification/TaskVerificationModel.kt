package com.yugyd.quiz.domain.tasks.models.verification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskVerificationModel(
    @SerialName("isCorrect") val isCorrect: Boolean,
    @SerialName("aiDescription") val aiDescription: String,
)
