package com.yugyd.quiz.data.ai.yandex.config

internal object YandexGptConfigs {
    const val TEMPERATURE = 0.6
    const val MAX_TOKEN = 20000

    /**
     * Yandex GPT models:
     *
     * - yandexgpt-lite [cheap, simple answers]
     * - yandexgpt [analysis text, generate code]
     * - yandexgpt-turbo [fast, chats]
     * - summarization [text summarization]
     */
    const val MODEL = "yandexgpt-lite"
}
