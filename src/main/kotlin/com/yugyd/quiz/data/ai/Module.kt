package com.yugyd.quiz.data.ai

import com.yugyd.quiz.data.ai.prompts.models.TaskPromptTypeModel
import com.yugyd.quiz.data.ai.prompts.models.ThemePromptTypeModel
import com.yugyd.quiz.data.ai.yandex.factory.YandexGptClientFactory
import com.yugyd.quiz.domain.ai.AiRepository
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import io.ktor.server.config.ApplicationConfig
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

internal fun aiDataModule(config: ApplicationConfig) = module {
    factory<AiClient> { (aiKeys: AiKeysModel?) ->
        val factory = get<YandexGptClientFactory>()
        factory.create(aiKeys)
    }

    factory<AiRepository> { (aiKeys: AiKeysModel?) ->
        val themeTypeProperty = config.property("customAi.themePromptStrategy").getString()

        val themeType = ThemePromptTypeModel.entries.firstOrNull {
            it.identifier == themeTypeProperty
        } ?: ThemePromptTypeModel.DEFAULT

        val taskTypeProperty = config.property("customAi.taskPromptStrategy").getString()
        val taskType = TaskPromptTypeModel.entries.firstOrNull {
            it.identifier == taskTypeProperty
        } ?: TaskPromptTypeModel.DEFAULT

        AiRepositoryImpl(
            aiClient = get(
                parameters = { parametersOf(aiKeys) }
            ),
            themePromptStrategy = get(
                parameters = { parametersOf(themeType) }
            ),
            tasksPromptStrategy = get(
                parameters = { parametersOf(taskType) }
            ),
            tasksVerificationPromptStrategy = get(
                parameters = { parametersOf(taskType) }
            ),
        )
    }
}
