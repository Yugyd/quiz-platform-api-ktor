package com.yugyd.quiz.data.ai.prompts.strategy.tasks

import com.yugyd.quiz.domain.ai.prompt.TasksVerificationPromptStrategy
import com.yugyd.quiz.domain.tasks.models.verification.TasksVerificationRequestModel

internal class DefaultTasksVerificationPromptStrategy : TasksVerificationPromptStrategy {

    override fun getPrompt(tasksVerification: TasksVerificationRequestModel): String {
        val template = """
        Compare the two answers. In the isCorrect field, indicate whether the user answered the question correctly. In
        the description field, indicate the description of the answer.

        Quest: ${tasksVerification.quest}
        Answer 1: ${tasksVerification.trueAnswer}   
        Answer 2: ${tasksVerification.userAnswer}
        
        The response must be in json format and consist of only one object from the isCorrect and aiDescription fields.

        Sample. Answer in json format:
        
        {
          "isCorrect": true,
          "aiDescription": "Chat GPT description"
        }
        """

        return template.trimIndent()
    }
}
