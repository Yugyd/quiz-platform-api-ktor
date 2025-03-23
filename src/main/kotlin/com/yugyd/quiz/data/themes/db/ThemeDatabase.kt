package com.yugyd.quiz.data.themes.db

import com.yugyd.quiz.core.coroutines.DispatchersProvider
import com.yugyd.quiz.data.themes.db.entities.ThemeDAO
import com.yugyd.quiz.data.themes.db.tables.ThemesTable
import com.yugyd.quiz.data.themes.mapper.ThemeEntityMapper
import com.yugyd.quiz.domain.theme.models.ThemeModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

internal class ThemeDatabase(
    private val database: Database,
    private val themeEntityMapper: ThemeEntityMapper,
    private val dispatchersProvider: DispatchersProvider,
) {

    fun initDatabase() {
        transaction(database) {
            SchemaUtils.create(ThemesTable)
        }
    }

    suspend fun getMainThemes(): List<ThemeModel> {
        return newSuspendedTransaction(dispatchersProvider.io) {
            ThemeDAO.find { ThemesTable.parentId.isNull() }.map(themeEntityMapper::map)
        }
    }

    suspend fun getThemes(parentId: Int): List<ThemeModel> {
        return newSuspendedTransaction(dispatchersProvider.io) {
            ThemeDAO.find { ThemesTable.parentId eq parentId }.map(themeEntityMapper::map)
        }
    }

    suspend fun getTheme(id: Int): ThemeModel? {
        return newSuspendedTransaction(dispatchersProvider.io) {
            ThemeDAO.findById(id)?.let(themeEntityMapper::map)
        }
    }

    suspend fun saveThemeContent(id: Int, content: String) {
        newSuspendedTransaction(dispatchersProvider.io) {
            ThemeDAO.findById(id)?.let { theme ->
                theme.content = content
            }
        }
    }

    suspend fun getThemeContent(id: Int): String? {
        return newSuspendedTransaction(dispatchersProvider.io) {
            ThemeDAO.findById(id)?.content
        }
    }
}
