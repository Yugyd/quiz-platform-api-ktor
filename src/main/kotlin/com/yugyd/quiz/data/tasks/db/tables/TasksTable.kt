package com.yugyd.quiz.data.tasks.db.tables

import com.yugyd.quiz.data.themes.db.tables.MAX_VARCHAR_LENGTH
import com.yugyd.quiz.data.themes.db.tables.ThemesTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

internal object TasksTable : IntIdTable(name = "tasks") {
    val quest = text("quest")
    val image = varchar("image", length = MAX_VARCHAR_LENGTH).nullable()
    val trueAnswer = text("true_answer")
    val answer2 = text("answer2").nullable()
    val answer3 = text("answer3").nullable()
    val answer4 = text("answer4").nullable()
    val answer5 = text("answer5").nullable()
    val answer6 = text("answer6").nullable()
    val answer7 = text("answer7").nullable()
    val answer8 = text("answer8").nullable()
    val complexity = integer("complexity")
    val category = reference("category", ThemesTable, onDelete = ReferenceOption.CASCADE)
    val section = integer("section")
    val type = varchar("type", length = MAX_VARCHAR_LENGTH).default("simple")
}
