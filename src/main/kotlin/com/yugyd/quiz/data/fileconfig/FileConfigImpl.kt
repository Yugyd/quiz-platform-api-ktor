package com.yugyd.quiz.data.fileconfig

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId

internal class FileConfigImpl : FileConfig {

    private val fileConfigs: MutableMap<String, String> = mutableMapOf()
    private var lastUpdatePromptsTime: LocalDateTime? = null

    private val mutex = Mutex()

    override suspend fun getConfig(filePath: String): String {
        return loadPromptFromFile(promptFilePath = filePath)
    }

    override suspend fun getConfigWithCaching(filePath: String, updateInterval: Duration): String {
        val currentTime = LocalDateTime.now()

        if (
            shouldUpdate(
                currentTime = currentTime,
                updateInterval = updateInterval,
                promptFilePath = filePath,
            )
        ) {
            mutex.withLock {
                if (
                    shouldUpdate(
                        currentTime = currentTime,
                        updateInterval = updateInterval,
                        promptFilePath = filePath,
                    )
                ) {
                    val newPrompt = loadPromptFromFile(filePath)
                    fileConfigs[filePath] = newPrompt
                    lastUpdatePromptsTime = currentTime
                }
            }
        }

        return fileConfigs[filePath].orEmpty()
    }

    private fun shouldUpdate(currentTime: LocalDateTime, updateInterval: Duration, promptFilePath: String): Boolean {
        val lastUpdate = lastUpdatePromptsTime ?: return true
        return currentTime.isAfter(lastUpdate.plus(updateInterval)) || isPromptChanged(promptFilePath)
    }

    private fun isPromptChanged(promptFilePath: String): Boolean {
        val promptFile = File(promptFilePath)

        val defaultZoneId = ZoneId.systemDefault()
        val lastUpdateTimeMills = lastUpdatePromptsTime?.atZone(defaultZoneId)?.toInstant()?.toEpochMilli() ?: 0

        return promptFile.lastModified() > lastUpdateTimeMills
    }

    private fun loadPromptFromFile(promptFilePath: String): String {
        val promptFile = File(promptFilePath)
        val promptText = promptFile.readText().trim()
        return promptText
    }
}
