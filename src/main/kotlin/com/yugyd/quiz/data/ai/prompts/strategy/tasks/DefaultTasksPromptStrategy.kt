package com.yugyd.quiz.data.ai.prompts.strategy.tasks

import com.yugyd.quiz.domain.ai.prompt.TasksPromptStrategy
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal class DefaultTasksPromptStrategy(
    private val tasksPromptConfig: TasksPromptConfig,
) : TasksPromptStrategy {

    override suspend fun getPrompt(theme: ThemeDetailModel): String {
        val template = """
        Generate tasks in json format. There should be ${tasksPromptConfig.tasksCount} tasks. The tasks should be on the topic below.

        Topic Title: "${theme.name}"
        Topic Content: "${theme.content}"

        Json format:
        [
            {
                "id": Int,
                "quest": String,
                "image": String,
                "trueAnswer": String,
                "answer2": String,
                "answer3": String,
                "answer4": String,
                "answer5": null,
                "answer6": null,
                "answer7": null,
                "answer8": null,
                "complexity": Int,
                "category": Int,
                "section": Int,
                "type": "simple"
            }
        ]

        Field "id" is unique identifier of the task.
        Fields "trueAnswer", "answer2", "answer3", "answer4" are always filled (not null or empty) and String type. Attention: Field "trueAnswer" is only String type and not json object or array.
        Fields "image", "answer5", "answer6", "answer7", "answer8" can be empty or null.
        Field "type" is always "simple".

        Sample:
        ""${'"'}
        // Start sample
        [
            {
                "id": 1",
                "quest": "What is the capital of France?",
                "image": null,
                "trueAnswer": "Paris",
                "answer2": "London",
                "answer3": "Berlin",
                "answer4": "Madrid",
                "answer5": null,
                "answer6": null,
                "answer7": null,
                "answer8": null,
                "complexity": 1,
                "category": 1,
                "section": 1,
                "type": "simple"
            },
            {
                "id": 2",
                "quest": "What is the capital of Spain?",
                "image": null,
                "trueAnswer": "Madrid",
                "answer2": "London",
                "answer3": "Berlin",
                "answer4": "Paris",
                "answer5": null,
                "answer6": null,
                "answer7": null,
                "answer8": null,
                "complexity": 1,
                "category": 1,
                "section": 1,
                "type": "simple"
            }
        ]
        // End sample
        ""${'"'}
        """

        return template.trimIndent()
    }

    companion object {
        const val DEFAULT_TASKS_COUNT = 10
    }
}
