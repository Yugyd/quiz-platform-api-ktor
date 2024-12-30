package com.yugyd.quiz.domain.ai

internal enum class AiProvider(val serverValue: String) {
    YANDEX(serverValue = "yandex"),
    OPENAI(serverValue = "openai"),
}
