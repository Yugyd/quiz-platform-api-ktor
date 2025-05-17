package com.yugyd.quiz.data.ai.chatgpt

import com.yugyd.quiz.data.ai.AiClient
import com.yugyd.quiz.data.ai.chatgpt.config.ChatGptAiKeys
import com.yugyd.quiz.data.ai.chatgpt.config.ChatGptConfigs
import com.yugyd.quiz.data.ai.chatgpt.entities.OpenAiResponse
import com.yugyd.quiz.data.ai.chatgpt.entities.OutputTextContentItemDto
import com.yugyd.quiz.data.ai.chatgpt.entities.request.OpenAiResponseRequest
import com.yugyd.quiz.data.ai.core.AiHttpClientConfig
import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException
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
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class ChatGptClientImpl(
    private val aiKeys: ChatGptAiKeys,
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
            json(
                Json(from = json) {
                    ignoreUnknownKeys = true
                    classDiscriminator = DYNAMIC_AI_RESPONSE_TYPE_QUALIFIER
                }
            )
        }
    }

    override suspend fun generateCompletion(prompt: String): String {
        val request = OpenAiResponseRequest(
            model = aiKeys.apiModel ?: ChatGptConfigs.MODEL,
            input = prompt,
            maxTokens = ChatGptConfigs.MAX_TOKENS,
            temperature = ChatGptConfigs.TEMPERATURE,
        )

        val response: HttpResponse = client.post(COMPLETIONS_API_URL) {
            header(HttpHeaders.Authorization, "Bearer ${aiKeys.apiKey}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        if (response.status == HttpStatusCode.Unauthorized) {
            throw InvalidAiCredentialException(response.status.description)
        }

        val completion = response.body<OpenAiResponse>()

        return completion.output
            .lastOrNull()
            ?.content
            ?.filterIsInstance<OutputTextContentItemDto>()
            ?.last()
            ?.text ?: throw NoSuchElementException("Text response is invalid format or nul")
    }

    private companion object {
        private const val DEFAULT_TIMEOUT = 60000L

        private const val DYNAMIC_AI_RESPONSE_TYPE_QUALIFIER = "type"

        private const val COMPLETIONS_API_URL = "https://api.openai.com/v1/responses"
    }
}
