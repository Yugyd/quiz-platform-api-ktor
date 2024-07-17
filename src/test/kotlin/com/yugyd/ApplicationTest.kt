package com.yugyd

import com.yugyd.data.themes.ThemesRepository
import com.yugyd.domain.content.models.ContentModel
import com.yugyd.domain.theme.models.details.ThemeDetailModel
import com.yugyd.domain.theme.models.list.ThemeListModel
import com.yugyd.domain.theme.models.list.ThemesRequestModel
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
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    private lateinit var themesRepository: ThemesRepository

    private val testContent = ContentModel.HISTORY
    private val testThemeId = 1
    private val testNotExistsThemeId = -1
    private val testParentThemeId = 2

    @BeforeTest
    fun setup() {
        themesRepository = mockk()

        coEvery {
            themesRepository.getThemes(testContent)
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
            themesRepository.getThemes(testContent, testParentThemeId)
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
            themesRepository.getThemeDetail(testThemeId)
        } returns ThemeDetailModel(
            id = 1,
            name = "Test Title",
            description = "Test Description",
            iconUrl = "Test Image Url",
            detail = true,
            content = "Test Content",
        )

        coEvery {
            themesRepository.getThemeDetail(testNotExistsThemeId)
        } returns null
    }

    private fun Application.testModule() {
//        configureSerialization()
//        configureDatabases(themesRepository, Json)
//        configureErrorHandling()
//        configureRouting(themesRepository)
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
        val themeId = 1

        // When
        val response = client.get("/theme/detail/$themeId")

        // Then
        val body = response.body<ThemeDetailModel>()
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            body,
            themesRepository.getThemeDetail(themeId)
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
            content = ContentModel.HISTORY.serverValue,
            parentThemeId = 2,
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
            themesRepository.getThemes(ContentModel.HISTORY, request.parentThemeId!!)
        )
    }
}
