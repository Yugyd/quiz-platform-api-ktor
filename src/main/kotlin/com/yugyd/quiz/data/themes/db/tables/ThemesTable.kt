package com.yugyd.quiz.data.themes.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

const val MAX_VARCHAR_LENGTH = 255

internal object ThemesTable : IntIdTable(name = "themes") {
    val code = varchar("alias_code", length = MAX_VARCHAR_LENGTH)
    val name = text("name")
    val description = text("description")
    val alternativeDescription = text("alternative_description").nullable()
    val iconUrl = varchar("icon_url", length = MAX_VARCHAR_LENGTH).nullable()
    val isFinal = bool("is_final").default(false)
    val parentId = reference("parent_id", ThemesTable, onDelete = ReferenceOption.CASCADE).nullable()
    val content = text("content").nullable()
}
