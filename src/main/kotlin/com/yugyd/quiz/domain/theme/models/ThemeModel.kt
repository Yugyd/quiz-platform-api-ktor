package com.yugyd.quiz.domain.theme.models

internal data class ThemeModel(
    val id: Int,
    val code: String,
    val name: String,
    val description: String,
    val alternativeDescription: String?,
    val iconUrl: String?,
    val isFinal: Boolean,
    val parentId: Int?,
)
