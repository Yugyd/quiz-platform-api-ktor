package com.yugyd.quiz.domain.theme.models.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ThemeListModel(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("iconUrl") val iconUrl: String?,
    @SerialName("detail") val detail: Boolean,
)
