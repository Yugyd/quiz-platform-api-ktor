package com.yugyd.quiz.data.ai

import com.yugyd.quiz.domain.ai.AiRepository
import com.yugyd.quiz.domain.ai.prompt.TasksPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.TasksVerificationPromptStrategy
import com.yugyd.quiz.domain.ai.prompt.ThemePromptStrategy
import com.yugyd.quiz.domain.tasks.models.verification.TasksVerificationRequestModel
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel

internal class AiRepositoryImpl(
    private val aiClient: AiClient,
    private val themePromptStrategy: ThemePromptStrategy,
    private val tasksPromptStrategy: TasksPromptStrategy,
    private val tasksVerificationPromptStrategy: TasksVerificationPromptStrategy,
) : AiRepository {

    override suspend fun generateCompletion(theme: ThemeDetailModel): String {
        val prompt = themePromptStrategy.getPrompt(theme)
        return aiClient.generateCompletion(prompt)
    }

    override suspend fun generateTasks(theme: ThemeDetailModel): String {
        val prompt = tasksPromptStrategy.getPrompt(theme)
        return aiClient.generateCompletion(prompt)
    }

    override suspend fun verifyTask(requestModel: TasksVerificationRequestModel): String {
        val prompt = tasksVerificationPromptStrategy.getPrompt(requestModel)
        return aiClient.generateCompletion(prompt)
    }
}
