package com.yugyd.data.themes.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

internal object ThemeRelationshipsTable : IntIdTable() {
    val parent = reference("parent", ThemesTable)
    val child = reference("child", ThemesTable)
}
