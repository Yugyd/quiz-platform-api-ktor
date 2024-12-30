package com.yugyd.quiz.data.tasks.db.entities

import com.yugyd.quiz.data.tasks.db.tables.TasksTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

internal class TasksDAO(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<TasksDAO>(TasksTable)

    var quest by TasksTable.quest
    var image by TasksTable.image
    var trueAnswer by TasksTable.trueAnswer
    var answer2 by TasksTable.answer2
    var answer3 by TasksTable.answer3
    var answer4 by TasksTable.answer4
    var answer5 by TasksTable.answer5
    var answer6 by TasksTable.answer6
    var answer7 by TasksTable.answer7
    var answer8 by TasksTable.answer8
    var complexity by TasksTable.complexity
    var category by TasksTable.category
    var section by TasksTable.section
    var type by TasksTable.type
}
