package com.yugyd.quiz.data.ai.core

internal data class AiHttpClientConfig(
    val keepAliveTime: Long,
    val connectTimeout: Long,
    val requestTimeout: Long,
)
