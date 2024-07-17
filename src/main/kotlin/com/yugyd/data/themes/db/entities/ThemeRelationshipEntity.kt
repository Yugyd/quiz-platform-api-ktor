package com.yugyd.data.themes.db.entities

import com.yugyd.data.themes.db.tables.ThemeRelationshipsTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

internal class ThemeRelationshipEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ThemeRelationshipEntity>(ThemeRelationshipsTable)

    var parent by ThemeEntity referencedOn ThemeRelationshipsTable.parent
    var child by ThemeEntity referencedOn ThemeRelationshipsTable.child
}
