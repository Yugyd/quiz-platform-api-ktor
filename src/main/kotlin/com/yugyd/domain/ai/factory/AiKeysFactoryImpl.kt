package com.yugyd.domain.ai.factory

import com.yugyd.data.themes.AiInitialException
import com.yugyd.domain.ai.AiProvider
import com.yugyd.domain.ai.models.AiAuthModel
import com.yugyd.domain.ai.models.AiKeysModel

internal class AiKeysFactoryImpl : AiKeysFactory {

    override fun create(aiAuthModel: AiAuthModel): AiKeysModel? {
        val aiKeys: AiKeysModel? = if (
            !aiAuthModel.aiKey.isNullOrEmpty() &&
            !aiAuthModel.aiFolder.isNullOrEmpty() &&
            !aiAuthModel.aiProvider.isNullOrEmpty()
        ) {
            val aiProvider = AiProvider.entries.firstOrNull {
                aiAuthModel.aiProvider == it.serverValue
            } ?: throw AiInitialException("Invalid AI provider")

            AiKeysModel(
                apiKey = aiAuthModel.aiKey,
                apiFolder = aiAuthModel.aiFolder,
                provider = aiProvider,
            )
        } else {
            null
        }

        return aiKeys
    }
}
