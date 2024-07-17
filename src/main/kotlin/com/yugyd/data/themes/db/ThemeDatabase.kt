package com.yugyd.data.themes.db

import com.yugyd.core.coroutines.DispatchersProvider
import com.yugyd.data.themes.db.entities.ThemeEntity
import com.yugyd.data.themes.db.entities.ThemeRelationshipEntity
import com.yugyd.data.themes.db.tables.ThemeRelationshipsTable
import com.yugyd.data.themes.db.tables.ThemesTable
import com.yugyd.data.themes.mapper.ThemeMapper
import com.yugyd.domain.theme.models.ThemeModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

internal class ThemeDatabase(
    database: Database,
    private val themeMapper: ThemeMapper,
    private val dispatchersProvider: DispatchersProvider,
) {

    init {
        transaction(database) {
            SchemaUtils.create(ThemesTable, ThemeRelationshipsTable)
        }
    }

    suspend fun getThemes(parentId: Int): List<ThemeModel> {
        return dbQuery {
            ThemeRelationshipEntity.find {
                ThemeRelationshipsTable.parent eq parentId
            }
                .map {
                    themeMapper.map(it.child)
                }
        }
    }

    suspend fun getThemes(): List<ThemeModel> {
        return dbQuery {
            ThemeEntity.all().map { themeEntity ->
                themeEntity.let(themeMapper::map)
            }
        }
    }

    suspend fun getTheme(id: Int): ThemeModel? {
        return dbQuery {
            ThemeEntity.findById(id)?.let(themeMapper::map)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(dispatchersProvider.io) { block() }
}
