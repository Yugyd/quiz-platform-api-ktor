package com.yugyd.data.ai

import com.yugyd.data.ai.prompts.models.PromptTypeModel
import com.yugyd.data.ai.yandex.factory.YandexGptClientFactory
import com.yugyd.domain.ai.AiRepository
import com.yugyd.domain.ai.models.AiKeysModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

private val defaultPromptTypeModel = PromptTypeModel.DEFAULT

internal val aiDataModule = module {
    factory<AiClient> { (aiKeys: AiKeysModel?) ->
        val factory = get<YandexGptClientFactory>()
        factory.create(aiKeys)
    }

    factory<AiRepository> { (aiKeys: AiKeysModel?) ->
        AiRepositoryImpl(
            aiClient = get(
                parameters = { parametersOf(aiKeys) }
            ),
            promptStrategy = get(
                parameters = { parametersOf(defaultPromptTypeModel) }
            ),
        )
    }
}
