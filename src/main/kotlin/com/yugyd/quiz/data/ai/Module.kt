package com.yugyd.quiz.data.ai

import com.yugyd.quiz.data.ai.prompts.models.PromptTypeModel
import com.yugyd.quiz.data.ai.yandex.factory.YandexGptClientFactory
import com.yugyd.quiz.domain.ai.AiRepository
import com.yugyd.quiz.domain.ai.models.AiKeysModel
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
            themePromptStrategy = get(
                parameters = { parametersOf(defaultPromptTypeModel) }
            ),
            tasksPromptStrategy = get(
                parameters = { parametersOf(defaultPromptTypeModel) }
            ),
            tasksVerificationPromptStrategy = get(
                parameters = { parametersOf(defaultPromptTypeModel) }
            ),
        )
    }
}
