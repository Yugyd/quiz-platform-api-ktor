package com.yugyd.domain.theme.models.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ThemesRequestModel(
    @SerialName("content") val content: String,
    @SerialName("parentThemeId") val parentThemeId: Int? = null,
)
