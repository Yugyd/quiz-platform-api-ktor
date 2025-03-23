package com.yugyd.quiz.data.ai.prompts.strategy.tasks

import com.yugyd.quiz.data.fileconfig.FileConfig

class TaskPromptFileConfig(
    private val fileConfig: FileConfig,
    private val taskFilePath: String,
) {

    suspend fun getPrompt(): String {
        return fileConfig.getConfigWithCaching(taskFilePath)
    }
}
