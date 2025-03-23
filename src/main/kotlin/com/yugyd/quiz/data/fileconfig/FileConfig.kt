package com.yugyd.quiz.data.fileconfig

import java.time.Duration

interface FileConfig {

    suspend fun getConfig(filePath: String): String

    suspend fun getConfigWithCaching(
        filePath: String,
        updateInterval: Duration = Duration.ofMinutes(15),
    ): String
}
