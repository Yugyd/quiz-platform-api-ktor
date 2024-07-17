package com.yugyd.data.themes.mapper

import com.yugyd.data.themes.db.entities.ThemeEntity
import com.yugyd.domain.theme.models.ThemeModel

internal class ThemeMapper {

    fun map(entity: ThemeEntity): ThemeModel {
        return ThemeModel(
            id = entity.id.value,
            code = entity.code,
            name = entity.name,
            description = entity.description,
            alternativeDescription = entity.alternativeDescription,
            iconUrl = entity.iconUrl,
            content = entity.content,
            childThemes = entity.childThemes.map { map(it.child) }
        )
    }
}
