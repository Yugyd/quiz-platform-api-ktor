package com.yugyd.quiz.data.ai

import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException

internal interface AiClient {

    @Throws(InvalidAiCredentialException::class)
    suspend fun generateCompletion(prompt: String): String
}
