package com.yugyd.data.ai.yandex

internal data class AiHttpClientConfig(
    val keepAliveTime: Long,
    val connectTimeout: Long,
    val requestTimeout: Long,
)