package com.yugyd.quiz.data.themes.db.entities

import com.yugyd.quiz.data.themes.db.tables.ThemesTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

internal class ThemeDAO(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<ThemeDAO>(ThemesTable)

    var code by ThemesTable.code
    var name by ThemesTable.name
    var description by ThemesTable.description
    val alternativeDescription by ThemesTable.alternativeDescription
    var iconUrl by ThemesTable.iconUrl
    var isFinal by ThemesTable.isFinal
    var parentId by ThemesTable.parentId
    var content by ThemesTable.content
}
