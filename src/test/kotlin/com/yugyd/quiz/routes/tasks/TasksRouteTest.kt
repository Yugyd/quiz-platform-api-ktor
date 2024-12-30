package com.yugyd.quiz.routes.tasks

import com.yugyd.quiz.configureDi
import com.yugyd.quiz.configureRouting
import com.yugyd.quiz.configureServer
import com.yugyd.quiz.domain.AiHeaders
import com.yugyd.quiz.domain.ai.AiProvider
import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.tasks.models.list.TaskListModel
import com.yugyd.quiz.domain.tasks.service.TasksService
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
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

class TasksRouteTest : KoinTest {

    private lateinit var tasksService: TasksService

    private val testContent = ContentModel.DEFAULT
    private val testAiKeysModel = AiKeysModel(
        apiKey = "Key",
        apiFolder = "Folder",
        provider = AiProvider.YANDEX,
    )
    private val testThemeId = 1
    private val testTasks = buildList {
        repeat(10) {
            add(
                TaskListModel(
                    id = it,
                    quest = "Quest $it",
                    trueAnswer = "True answer $it",
                    complexity = it,
                    section = it,
                    category = it,
                )
            )
        }
    }

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
    fun produces200_WHEN_getTasks() = testApplication {
        // Given
        coEvery {
            tasksService.getTasks(content = testContent, themeId = testThemeId, aiKeys = testAiKeysModel)
        } returns testTasks

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
        val response = client.get("/tasks/$testThemeId") {
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
        }
        val result = response.body<List<TaskListModel>>()

        // Then
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(testTasks, result)
    }

    @Test
    fun produces429_WHEN_getTasks() = testApplication {
        // Given
        coEvery {
            tasksService.getTasks(content = testContent, themeId = testThemeId, aiKeys = testAiKeysModel)
        } returns testTasks

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
            val response = client.get("/tasks/$testThemeId") {
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
            }
            assertEquals(HttpStatusCode.OK, response.status)
        }

        val response = client.get("/tasks/$testThemeId")

        // Then
        assertEquals(HttpStatusCode.TooManyRequests, response.status)
    }

    @Test
    fun produces404_WHEN_getTasks() = testApplication {
        // Given
        coEvery {
            tasksService.getTasks(content = testContent, themeId = testThemeId, aiKeys = testAiKeysModel)
        } returns emptyList()

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
        val response = client.get("/tasks/$testThemeId") {
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
        }

        // Then
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun produces400_WHEN_getTasksWithInvalidThemeId() = testApplication {
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
        val response = client.get("/tasks/Invalid") {
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
        }

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces400_WHEN_getTasks_ifAiProviderNotFounded() = testApplication {
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
        val response = client.get("/tasks/$testThemeId") {
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
        }

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces400_WHEN_getTasks_ifAiHeadersIsNull() = testApplication {
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
        val response = client.get("/tasks/$testThemeId") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
        }

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces401_WHEN_getTasks_ifAiKeyIsInvalid() = testApplication {
        // Given
        val testInvalidApiKey = "Invalid key"
        coEvery {
            tasksService.getTasks(
                testContent,
                testThemeId,
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
        val response = client.get("/tasks/$testThemeId") {
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
        }

        // Then
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun produces400_WHEN_getTasks_ifJsonResponseInvalid() = testApplication {
        // Given
        coEvery {
            tasksService.getTasks(testContent, testThemeId, testAiKeysModel)
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
        val response = client.get("/tasks/$testThemeId") {
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
        }

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}
