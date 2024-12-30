package com.yugyd.quiz.data.ai.prompts

import com.yugyd.quiz.domain.ai.prompt.TasksPromptStrategy
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal class DefaultTasksPromptStrategy : TasksPromptStrategy {

    override fun getPrompt(theme: ThemeDetailModel): String {
        val template = """
        Generate tasks in json format. There should be 10 or more tasks. The tasks should be on the topic below.
        Topic Title: ${theme.name}
        Topic Short Description: ${theme.description}
        Topic: ${theme.content}
        
        Fields trueAnswer, answer2, answer3, answer4 are always filled and String type. Not json object and not array.
        Fields answer5, answer6, answer7, answer8 are always null.
        
        Sample. Tasks in json format:
        [
            {
                "id": Int,
                "quest": String,
                "image": String,
                "trueAnswer": String,
                "answer2": String,
                "answer3": String,
                "answer4": String,
                "answer5": null, (always)
                "answer6": null, (always)
                "answer7": null, (always)
                "answer8": null, (always)
                "complexity": Int,
                "category": Int,
                "section": Int,
                "type": "simple" (always)
            }
        ]
        """

        return template.trimIndent()
    }
}
