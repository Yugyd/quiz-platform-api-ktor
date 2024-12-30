package com.yugyd.quiz.domain.tasks.models.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskListModel(
    @SerialName("id") val id: Int,
    @SerialName("quest") val quest: String,
    @SerialName("image") val image: String? = null,
    @SerialName("trueAnswer") val trueAnswer: String,
    @SerialName("answer2") val answer2: String? = null,
    @SerialName("answer3") val answer3: String? = null,
    @SerialName("answer4") val answer4: String? = null,
    @SerialName("answer5") val answer5: String? = null,
    @SerialName("answer6") val answer6: String? = null,
    @SerialName("answer7") val answer7: String? = null,
    @SerialName("answer8") val answer8: String? = null,
    @SerialName("complexity") val complexity: Int,
    @SerialName("category") val category: Int,
    @SerialName("section") val section: Int,
    @SerialName("type") val type: String = "simple",
)

