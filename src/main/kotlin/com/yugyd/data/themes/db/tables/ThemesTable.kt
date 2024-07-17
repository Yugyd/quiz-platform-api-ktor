package com.yugyd.data.themes.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

internal object ThemesTable : IntIdTable() {
    val code = varchar("code", length = 50)
    val name = text("name")
    val description = text("description").nullable()
    val alternativeDescription = text("alternativeDescription").nullable()
    val iconUrl = varchar("iconUrl", length = 255).nullable()
    val content = text("content").nullable()
    val childThemes = reference("childThemes", ThemesTable.id).nullable()
}
