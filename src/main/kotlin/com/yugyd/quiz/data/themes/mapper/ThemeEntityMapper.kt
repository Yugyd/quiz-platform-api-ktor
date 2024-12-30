package com.yugyd.quiz.data.themes.mapper

import com.yugyd.quiz.data.themes.db.entities.ThemeDAO
import com.yugyd.quiz.domain.theme.models.ThemeModel

internal class ThemeEntityMapper {

    fun map(entity: ThemeDAO): ThemeModel {
        return ThemeModel(
            id = entity.id.value,
            code = entity.code,
            name = entity.name,
            description = entity.description,
            alternativeDescription = entity.alternativeDescription,
            iconUrl = entity.iconUrl,
            isFinal = entity.isFinal,
            parentId = entity.parentId?.value,
        )
    }
}
