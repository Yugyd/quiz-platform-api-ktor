package com.yugyd.data.themes.db.entities

import com.yugyd.data.themes.db.tables.ThemeRelationshipsTable
import com.yugyd.data.themes.db.tables.ThemesTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

internal class ThemeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ThemeEntity>(ThemesTable)

    var code by ThemesTable.code
    var name by ThemesTable.name
    var description by ThemesTable.description
    val alternativeDescription by ThemesTable.alternativeDescription
    var iconUrl by ThemesTable.iconUrl
    var content by ThemesTable.content
    val childThemes by ThemeRelationshipEntity referrersOn ThemeRelationshipsTable.parent
}
