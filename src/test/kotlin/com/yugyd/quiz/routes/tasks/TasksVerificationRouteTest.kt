package com.yugyd.quiz.routes.tasks

import com.yugyd.quiz.configureDi
import com.yugyd.quiz.configureRouting
import com.yugyd.quiz.configureServer
import com.yugyd.quiz.domain.AiHeaders
import com.yugyd.quiz.domain.ai.AiProvider
import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.tasks.models.verification.TaskVerificationModel
import com.yugyd.quiz.domain.tasks.models.verification.TasksVerificationRequestModel
import com.yugyd.quiz.domain.tasks.service.TasksService
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.testing.testApplication
import io.mockk.coEvery
import io.mockk.mockk
import org.jetbrains.exposed.sql.Database
import org.koin.core.Koin
import org.koin.ktor.ext.getKoin
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TasksVerificationRouteTest : KoinTest {

    private lateinit var tasksService: TasksService

    private val testContent = ContentModel.DEFAULT
    private val testVerificationTaskRequest = TasksVerificationRequestModel(
        quest = "Quest",
        userAnswer = "User answer",
        trueAnswer = "True answer",
    )
    private val testAiKeysModel = AiKeysModel(
        apiKey = "Key",
        apiFolder = "Folder",
        provider = AiProvider.YANDEX,
        aiModel = null,
    )
    private val testVerificationTaskResponse = TaskVerificationModel(
        isCorrect = true,
        aiDescription = "Ai description"
    )

    @BeforeTest
    fun setup() {
        tasksService = mockk()
    }

    private inline fun Application.module(
        koinInit: (Koin) -> Unit,
    ) {
        configureDi()
        koinInit(getKoin())
        configureServer()
        configureRouting()
    }

    @Test
    fun produces200_WHEN_postVerification() = testApplication {
        // Given
        coEvery {
            tasksService.verifyTask(testContent, testVerificationTaskRequest, testAiKeysModel)
        } returns testVerificationTaskResponse

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(tasksService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.post("/tasks/verification") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            header(
                AiHeaders.KEY_HEADER,
                testAiKeysModel.apiKey,
            )
            header(
                AiHeaders.FOLDER_HEADER,
                testAiKeysModel.apiFolder,
            )
            header(
                AiHeaders.PROVIDER_HEADER,
                testAiKeysModel.provider.serverValue,
            )

            setBody(testVerificationTaskRequest)
        }
        val result = response.body<TaskVerificationModel>()

        // Then
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(testVerificationTaskResponse, result)
    }

    @Test
    fun produces429_WHEN_postVerification() = testApplication {
        // Given
        coEvery {
            tasksService.verifyTask(testContent, testVerificationTaskRequest, testAiKeysModel)
        } returns testVerificationTaskResponse

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(tasksService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        repeat(100) {
            val response = client.post("/tasks/verification") {
                header(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json
                )
                header(
                    AiHeaders.KEY_HEADER,
                    testAiKeysModel.apiKey,
                )
                header(
                    AiHeaders.FOLDER_HEADER,
                    testAiKeysModel.apiFolder,
                )
                header(
                    AiHeaders.PROVIDER_HEADER,
                    testAiKeysModel.provider.serverValue,
                )

                setBody(testVerificationTaskRequest)
            }
            assertEquals(HttpStatusCode.OK, response.status)
        }

        val response = client.post("/tasks/verification") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            header(
                AiHeaders.KEY_HEADER,
                testAiKeysModel.apiKey,
            )
            header(
                AiHeaders.FOLDER_HEADER,
                testAiKeysModel.apiFolder,
            )
            header(
                AiHeaders.PROVIDER_HEADER,
                testAiKeysModel.provider.serverValue,
            )

            setBody(testVerificationTaskRequest)
        }

        // Then
        assertEquals(HttpStatusCode.TooManyRequests, response.status)
    }

    @Test
    fun produces400_WHEN_postVerification() = testApplication {
        // Given
        application {
            module {
                it.declare<Database>(mockk())
                it.declare(tasksService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.post("/tasks/verification") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            header(
                AiHeaders.KEY_HEADER,
                testAiKeysModel.apiKey,
            )
            header(
                AiHeaders.FOLDER_HEADER,
                testAiKeysModel.apiFolder,
            )
            header(
                AiHeaders.PROVIDER_HEADER,
                testAiKeysModel.provider.serverValue,
            )

            setBody("{\"invalidFiled\": \"Invalid value\"}")
        }

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces400_WHEN_postVerification_ifValueInBodyIsEmpty() = testApplication {
        // Given
        application {
            module {
                it.declare<Database>(mockk())
                it.declare(tasksService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.post("/tasks/verification") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            header(
                AiHeaders.KEY_HEADER,
                testAiKeysModel.apiKey,
            )
            header(
                AiHeaders.FOLDER_HEADER,
                testAiKeysModel.apiFolder,
            )
            header(
                AiHeaders.PROVIDER_HEADER,
                testAiKeysModel.provider.serverValue,
            )

            setBody(
                """
                {
                    "quest": "",
                    "userAnswer": "User answer",
                    "trueAnswer": "True answer"
                }
                """.trimIndent()
            )
        }

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces400_WHEN_postVerification_ifAiProviderNotFounded() = testApplication {
        // Given
        application {
            module {
                it.declare<Database>(mockk())
                it.declare(tasksService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.post("/tasks/verification") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            header(
                AiHeaders.KEY_HEADER,
                testAiKeysModel.apiKey,
            )
            header(
                AiHeaders.FOLDER_HEADER,
                testAiKeysModel.apiFolder,
            )
            header(
                AiHeaders.PROVIDER_HEADER,
                "Invalid",
            )

            setBody(testVerificationTaskRequest)
        }

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces400_WHEN_postVerification_ifAiHeadersIsNull() = testApplication {
        // Given
        application {
            module {
                it.declare<Database>(mockk())
                it.declare(tasksService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.post("/tasks/verification") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )

            setBody(testVerificationTaskRequest)
        }

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces401_WHEN_postVerification_ifAiKeyIsInvalid() = testApplication {
        // Given
        val testInvalidApiKey = "Invalid key"
        coEvery {
            tasksService.verifyTask(
                testContent,
                testVerificationTaskRequest,
                testAiKeysModel.copy(apiKey = testInvalidApiKey)
            )
        } throws InvalidAiCredentialException("Invalid ai keys")

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(tasksService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.post("/tasks/verification") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            header(
                AiHeaders.KEY_HEADER,
                testInvalidApiKey,
            )
            header(
                AiHeaders.FOLDER_HEADER,
                testAiKeysModel.apiFolder,
            )
            header(
                AiHeaders.PROVIDER_HEADER,
                testAiKeysModel.provider.serverValue,
            )

            setBody(testVerificationTaskRequest)
        }

        // Then
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun produces400_WHEN_postVerification_ifJsonResponseInvalid() = testApplication {
        // Given
        coEvery {
            tasksService.verifyTask(testContent, testVerificationTaskRequest, testAiKeysModel)
        } throws JsonConvertException("Invalid json")

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(tasksService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.post("/tasks/verification") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            header(
                AiHeaders.KEY_HEADER,
                testAiKeysModel.apiKey,
            )
            header(
                AiHeaders.FOLDER_HEADER,
                testAiKeysModel.apiFolder,
            )
            header(
                AiHeaders.PROVIDER_HEADER,
                testAiKeysModel.provider.serverValue,
            )

            setBody(testVerificationTaskRequest)
        }

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}
