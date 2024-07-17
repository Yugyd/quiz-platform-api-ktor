package com.yugyd.data.ai

import com.yugyd.domain.ai.AiRepository
import com.yugyd.domain.ai.prompt.PromptStrategy
import com.yugyd.domain.theme.models.details.ThemeDetailModel

internal class AiRepositoryImpl(
    private val aiClient: AiClient,
    private val promptStrategy: PromptStrategy,
) : AiRepository {

    override suspend fun generateCompletion(theme: ThemeDetailModel): String {
        val prompt = promptStrategy.getPrompt(theme)
        return aiClient.generateCompletion(prompt)
    }
}
