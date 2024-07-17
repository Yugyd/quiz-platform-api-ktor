package com.yugyd.data.ai

internal interface AiClient {
    suspend fun generateCompletion(prompt: String): String
}
