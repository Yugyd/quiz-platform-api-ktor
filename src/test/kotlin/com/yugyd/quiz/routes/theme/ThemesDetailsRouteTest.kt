package com.yugyd.quiz.routes.theme

import com.yugyd.quiz.configureDi
import com.yugyd.quiz.configureRouting
import com.yugyd.quiz.configureServer
import com.yugyd.quiz.domain.AiHeaders
import com.yugyd.quiz.domain.ai.AiProvider
import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException
import com.yugyd.quiz.domain.ai.models.AiKeysModel
import com.yugyd.quiz.domain.theme.models.details.ThemeDetailModel
import com.yugyd.quiz.domain.theme.service.ThemeService
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
import kotlinx.io.IOException
import org.jetbrains.exposed.sql.Database
import org.koin.core.Koin
import org.koin.ktor.ext.getKoin
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ThemesDetailsRouteTest : KoinTest {

    private lateinit var themesService: ThemeService

    private val testAiKeysModel = AiKeysModel(
        apiKey = "Key",
        apiFolder = "Folder",
        provider = AiProvider.YANDEX,
        aiModel = null,
    )
    private val testThemeId = 1
    private val testThemeDetail = ThemeDetailModel(
        id = testThemeId,
        name = "Test Title",
        description = "Test Description",
        iconUrl = "Test Image Url",
        detail = true,
        content = "Test Content",
    )
    private val testRecreatedThemeDetail = ThemeDetailModel(
        id = testThemeId,
        name = "Test Title",
        description = "Test Description",
        iconUrl = "Test Image Url",
        detail = true,
        content = "Recreated test Content",
    )

    @BeforeTest
    fun setup() {
        themesService = mockk()
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
            themesService.getThemeDetail(
                id = testThemeId,
                recreate = false,
                aiKeys = testAiKeysModel,
            )
        } returns testThemeDetail

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/themes/$testThemeId") {
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
        val result = response.body<ThemeDetailModel>()

        // Then
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(testThemeDetail, result)
    }

    @Test
    fun produces429_WHEN_getTasks() = testApplication {
        // Given
        coEvery {
            themesService.getThemeDetail(
                id = testThemeId,
                recreate = false,
                aiKeys = testAiKeysModel,
            )
        } returns testThemeDetail
        coEvery {
            themesService.getThemeDetail(
                id = testThemeId,
                recreate = true,
                aiKeys = testAiKeysModel,
            )
        } returns testRecreatedThemeDetail

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        repeat(1000) {
            val response = client.get("/themes/$testThemeId?recreate=false") {
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

        val response = client.get("/themes/$testThemeId?recreate=false")

        // Then
        assertEquals(HttpStatusCode.TooManyRequests, response.status)
    }

    @Test
    fun produces200_WHEN_getTasksWithRecreate() = testApplication {
        // Given
        coEvery {
            themesService.getThemeDetail(
                id = testThemeId,
                recreate = true,
                aiKeys = testAiKeysModel,
            )
        } returns testRecreatedThemeDetail
        coEvery {
            themesService.getThemeDetail(
                id = testThemeId,
                recreate = false,
                aiKeys = testAiKeysModel,
            )
        } returns testThemeDetail

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/themes/$testThemeId?recreate=true") {
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
        val result = response.body<ThemeDetailModel>()

        // Then
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(testRecreatedThemeDetail, result)
    }

    @Test
    fun produces404_WHEN_getTasks() = testApplication {
        // Given
        coEvery {
            themesService.getThemeDetail(
                id = testThemeId,
                recreate = false,
                aiKeys = testAiKeysModel,
            )
        } returns null

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/themes/$testThemeId") {
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
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/themes/Invalid") {
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
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/themes/$testThemeId") {
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
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/themes/$testThemeId") {
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
            themesService.getThemeDetail(
                id = testThemeId,
                recreate = false,
                aiKeys = testAiKeysModel.copy(apiKey = testInvalidApiKey),
            )
        } throws InvalidAiCredentialException("Invalid ai keys")

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/themes/$testThemeId") {
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
            themesService.getThemeDetail(
                id = testThemeId,
                recreate = false,
                aiKeys = testAiKeysModel,
            )
        } throws JsonConvertException("Invalid json")

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/themes/$testThemeId") {
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
    fun produces500_WHEN_getTasks_afterIOException() = testApplication {
        // Given
        coEvery {
            themesService.getThemeDetail(
                id = testThemeId,
                recreate = false,
                aiKeys = testAiKeysModel,
            )
        } throws IOException("Database error")

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/themes/$testThemeId") {
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
        assertEquals(HttpStatusCode.InternalServerError, response.status)
    }
}
