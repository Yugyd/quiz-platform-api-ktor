package com.yugyd.quiz.data.ai

import com.yugyd.quiz.data.ai.prompts.models.TaskPromptTypeModel
import com.yugyd.quiz.data.ai.prompts.models.ThemePromptTypeModel
import com.yugyd.quiz.domain.ai.AiProvider
import com.yugyd.quiz.domain.ai.AiRepository
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import io.ktor.server.config.ApplicationConfig
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val YANDEX_AI_QUALIFIER = named("yandex")
internal val CHATGPT_AI_QUALIFIER = named("chatgpt")

internal fun aiDataModule(config: ApplicationConfig) = module {
    factory<AiClient>(CHATGPT_AI_QUALIFIER) { (aiKeys: AiKeysModel?) ->
        val factory = get<AiClientFactory>(CHATGPT_AI_QUALIFIER)
        factory.create(aiKeys)
    }

    factory<AiClient>(YANDEX_AI_QUALIFIER) { (aiKeys: AiKeysModel?) ->
        val factory = get<AiClientFactory>(YANDEX_AI_QUALIFIER)
        factory.create(aiKeys)
    }

    factory<AiRepository> { (aiKeys: AiKeysModel) ->
        val themeTypeProperty = config.property("customAi.themePromptStrategy").getString()

        val themeType = ThemePromptTypeModel.entries.firstOrNull {
            it.identifier == themeTypeProperty
        } ?: ThemePromptTypeModel.DEFAULT

        val taskTypeProperty = config.property("customAi.taskPromptStrategy").getString()
        val taskType = TaskPromptTypeModel.entries.firstOrNull {
            it.identifier == taskTypeProperty
        } ?: TaskPromptTypeModel.DEFAULT

        // Refactoring use SOLID pattern: OCP. Move this logic to AiClientFactory
        val aiClient: AiClient = when (aiKeys.provider) {
            AiProvider.YANDEX -> {
                get(
                    parameters = { parametersOf(aiKeys) },
                    qualifier = YANDEX_AI_QUALIFIER,
                )
            }

            AiProvider.OPENAI -> {
                get(
                    parameters = { parametersOf(aiKeys) },
                    qualifier = CHATGPT_AI_QUALIFIER,
                )
            }
        }

        AiRepositoryImpl(
            aiClient = aiClient,
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
