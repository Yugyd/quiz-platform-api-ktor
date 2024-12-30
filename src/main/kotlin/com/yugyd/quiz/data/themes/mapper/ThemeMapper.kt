package com.yugyd.quiz.data.themes.mapper

import com.yugyd.quiz.domain.theme.models.ThemeModel
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel
import com.yugyd.quiz.domain.theme.models.list.ThemeListModel

internal class ThemeMapper {

    fun mapToListModel(model: ThemeModel): ThemeListModel {
        return ThemeListModel(
            id = model.id,
            name = model.name,
            description = model.description,
            iconUrl = model.iconUrl,
            detail = model.isFinal,
        )
    }

    fun mapToThemeDetail(model: ThemeModel): ThemeDetailModel {
        return ThemeDetailModel(
            id = model.id,
            name = model.name,
            description = model.description,
            iconUrl = model.iconUrl,
            detail = model.isFinal,
            content = "",
        )
    }
}
