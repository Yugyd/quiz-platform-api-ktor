package com.yugyd.quiz.domain.ai.factory

import com.yugyd.quiz.data.themes.AiInitialException
import com.yugyd.quiz.domain.ai.AiProvider
import com.yugyd.quiz.domain.ai.models.AiAuthModel
import com.yugyd.quiz.domain.ai.models.AiKeysModel

internal class AiKeysFactoryImpl : AiKeysFactory {

    override fun create(aiAuthModel: AiAuthModel): AiKeysModel? {
        val aiKeys: AiKeysModel? = if (
            !aiAuthModel.aiKey.isNullOrEmpty() &&
            !aiAuthModel.aiProvider.isNullOrEmpty()
        ) {
            if (
                aiAuthModel.aiProvider == AiProvider.YANDEX.serverValue &&
                aiAuthModel.aiFolder.isNullOrEmpty()
            ) {
                return null
            }

            val aiProvider = AiProvider.entries.firstOrNull {
                aiAuthModel.aiProvider == it.serverValue
            } ?: throw AiInitialException("Invalid AI provider")

            AiKeysModel(
                apiKey = aiAuthModel.aiKey,
                apiFolder = aiAuthModel.aiFolder,
                provider = aiProvider,
                aiModel = aiAuthModel.aiModel,
            )
        } else {
            null
        }

        return aiKeys
    }
}
