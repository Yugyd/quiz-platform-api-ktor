package com.yugyd.quiz.data.tasks.mapper

import com.yugyd.quiz.data.tasks.db.entities.TasksDAO
import com.yugyd.quiz.domain.tasks.models.list.TaskListModel

internal class TaskEntityMapper {

    fun map(entity: TasksDAO): TaskListModel {
        return TaskListModel(
            id = entity.id.value,
            quest = entity.quest,
            image = entity.image,
            trueAnswer = entity.trueAnswer,
            answer2 = entity.answer2,
            answer3 = entity.answer3,
            answer4 = entity.answer4,
            answer5 = entity.answer5,
            answer6 = entity.answer6,
            answer7 = entity.answer7,
            answer8 = entity.answer8,
            complexity = entity.complexity,
            category = entity.category.value,
            section = entity.section,
            type = entity.type,
        )
    }
}
