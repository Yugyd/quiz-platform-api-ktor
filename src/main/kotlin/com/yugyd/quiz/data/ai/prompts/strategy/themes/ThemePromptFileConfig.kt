package com.yugyd.quiz.data.ai.prompts.strategy.themes

import com.yugyd.quiz.data.fileconfig.FileConfig

class ThemePromptFileConfig(
    private val fileConfig: FileConfig,
    private val themeFilePath: String,
) {
    suspend fun getPrompt(): String {
        return fileConfig.getConfigWithCaching(themeFilePath)
    }
}
