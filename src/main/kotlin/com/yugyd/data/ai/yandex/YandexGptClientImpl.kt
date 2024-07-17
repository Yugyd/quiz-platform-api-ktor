package com.yugyd.data.ai.yandex

import com.yugyd.data.ai.AiClient
import com.yugyd.data.ai.yandex.config.YandexAiKeys
import com.yugyd.data.ai.yandex.config.YandexGptConfigs
import com.yugyd.data.ai.yandex.entities.MessageDao
import com.yugyd.data.ai.yandex.entities.ResultResponse
import com.yugyd.data.ai.yandex.entities.RoleModelDao
import com.yugyd.data.ai.yandex.entities.request.CompletionOptionsDao
import com.yugyd.data.ai.yandex.entities.request.CompletionRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class YandexGptClientImpl(
    private val yandexAiKeys: YandexAiKeys,
    private val json: Json,
    private val aiConfig: AiHttpClientConfig?,
) : AiClient {

    private val client = HttpClient(CIO) {
        engine {
            endpoint {
                keepAliveTime = aiConfig?.keepAliveTime ?: DEFAULT_TIMEOUT
                connectTimeout = aiConfig?.connectTimeout ?: DEFAULT_TIMEOUT
                requestTimeout = aiConfig?.requestTimeout ?: DEFAULT_TIMEOUT
            }
        }

        install(ContentNegotiation) {
            json(json)
        }
    }

    override suspend fun generateCompletion(prompt: String): String {
        val completionOptions = CompletionOptionsDao(
            stream = false,
            temperature = YandexGptConfigs.TEMPERATURE,
            maxTokens = YandexGptConfigs.MAX_TOKEN,
        )
        val request = CompletionRequest(
            modelUri = "gpt://${yandexAiKeys.apiFolder}/yandexgpt/latest",
            completionOptions = completionOptions,
            messages = listOf(
                MessageDao(
                    role = RoleModelDao.USER,
                    text = prompt,
                ),
            ),
        )

        val response: HttpResponse = client.post(COMPLETIONS_API_URL) {
            header(HttpHeaders.Authorization, "Api-Key ${yandexAiKeys.apiKey}")
            header("x-folder-id", yandexAiKeys.apiFolder)
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        val completion = response.body<ResultResponse>()

        return completion.result.alternatives.last().message.text
    }

    private companion object {
        private const val DEFAULT_TIMEOUT = 60000L

        private const val COMPLETIONS_API_URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"
    }
}

