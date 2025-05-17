package com.yugyd.quiz.data.ai.chatgpt.config

internal object ChatGptConfigs {
    const val TEMPERATURE = 0.7

    /**
     * Chat GPT models:
     *
     * https://platform.openai.com/docs/models
     *
     * - gpt-4.1 [Flagship GPT model for complex tasks]
     * - gpt-4.1-mini [Balanced for intelligence, speed, and cost]
     * - gpt-4.1-nano [Fastest, most cost-effective GPT-4.1 model]
     * - gpt-4o [Fast, intelligent, flexible GPT model]
     * - gpt-4o-mini [Fast, affordable small model for focused tasks]
     */
    const val MODEL = "gpt-4.1-mini"

    const val MAX_TOKENS = 20000
}
