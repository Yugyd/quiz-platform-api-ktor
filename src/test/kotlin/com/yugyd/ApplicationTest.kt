package com.yugyd

import com.yugyd.app.configureErrorHandling
import com.yugyd.app.configureRouting
import com.yugyd.app.configureSerialization
import com.yugyd.core.serializationCoreModule
import com.yugyd.domain.ai.factory.AiKeysFactory
import com.yugyd.domain.ai.factory.AiKeysFactoryImpl
import com.yugyd.domain.content.models.ContentModel
import com.yugyd.domain.theme.models.details.ThemeDetailModel
import com.yugyd.domain.theme.models.list.ThemeListModel
import com.yugyd.domain.theme.models.list.ThemesRequestModel
import com.yugyd.domain.theme.service.ThemeService
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.testing.testApplication
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest : KoinTest {

    private val testContent = ContentModel.HISTORY
    private val testThemeId = 1
    private val testNotExistsThemeId = -1
    private val testParentThemeId = 2

    private lateinit var aiKeysFactory: AiKeysFactory

    private lateinit var themeService: ThemeService

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            serializationCoreModule,
            module {
                single<AiKeysFactory> { aiKeysFactory }
                single<ThemeService> { themeService }
            },
        )
    }

    @BeforeTest
    fun setup() {
        aiKeysFactory = AiKeysFactoryImpl()

        themeService = mockk<ThemeService>()
        coEvery {
            themeService.getThemes(testContent)
        } returns listOf(
            ThemeListModel(
                id = 1,
                name = "Test Title",
                description = "Test Description",
                iconUrl = "Test Image Url",
                detail = false,
            )
        )

        coEvery {
            themeService.getThemes(testContent, testParentThemeId)
        } returns listOf(
            ThemeListModel(
                id = 2,
                name = "Test Title",
                description = "Test Description",
                iconUrl = "Test Image Url",
                detail = true,
            )
        )

        coEvery {
            themeService.getThemeDetail(testThemeId)
        } returns ThemeDetailModel(
            id = 1,
            name = "Test Title",
            description = "Test Description",
            iconUrl = "Test Image Url",
            detail = true,
            content = "Test Content",
        )

        coEvery {
            themeService.getThemeDetail(testNotExistsThemeId)
        } returns null
    }

    @Test
    fun produces400_WHEN_getThemeDetail_invalidThemeId() = testApplication {
        // Given
        application {
            testModule()
        }

        // When
        val response = client.get("/theme/detail/Invalid")

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces404_WHEN_getThemeDetail_unusedThemeId() = testApplication {
        // Given
        application {
            testModule()
        }

        // When
        val response = client.get("/theme/detail/$testNotExistsThemeId")

        // Then
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun produces200_WHEN_getThemeDetail() = testApplication {
        // Given
        application {
            testModule()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // When
        val response = client.get("/theme/detail/$testThemeId")

        // Then
        val body = response.body<ThemeDetailModel>()
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            body,
            themeService.getThemeDetail(testThemeId)
        )
    }

    @Test
    fun produces200_WHEN_getThemes() = testApplication {
        application {
            testModule()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val request = ThemesRequestModel(
            content = testContent.serverValue,
            parentThemeId = testParentThemeId,
        )

        // When
        val response = client.post("/theme/themes") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(request)
        }

        // Then
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            response.body<List<ThemeListModel>>(),
            themeService.getThemes(testContent, testParentThemeId)
        )
    }

    private fun Application.testModule() {
        configureSerialization()
        configureErrorHandling()
        configureRouting()
    }
}
