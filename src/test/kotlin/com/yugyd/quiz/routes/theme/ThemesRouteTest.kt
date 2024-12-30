package com.yugyd.quiz.routes.theme

import com.yugyd.quiz.configureDi
import com.yugyd.quiz.configureRouting
import com.yugyd.quiz.configureServer
import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.theme.models.list.ThemeListModel
import com.yugyd.quiz.domain.theme.service.ThemeService
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
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
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ThemesRouteTest : KoinTest {

    private lateinit var themesService: ThemeService

    private val testContent = ContentModel.DEFAULT
    private val testThemeId = 1
    private val testNotExistsThemeId = -1
    private val testParentThemeId = 2

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
    fun produces200_WHEN_getThemes() = testApplication {
        // Given
        val testResults = listOf(
            ThemeListModel(
                id = testParentThemeId,
                name = "Test Title",
                description = "Test Description",
                iconUrl = "Test Image Url",
                detail = false,
            )
        )
        coEvery {
            themesService.getThemes(testContent)
        } returns testResults

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
        val response = client.get("/themes")
        val results = response.body<List<ThemeListModel>>()

        // Then
        assertEquals(HttpStatusCode.OK, response.status)
        assertContentEquals(testResults, results)
    }

    @Test
    fun produces429_WHEN_getThemes() = testApplication {
        // Given
        val testResults = listOf(
            ThemeListModel(
                id = testParentThemeId,
                name = "Test Title",
                description = "Test Description",
                iconUrl = "Test Image Url",
                detail = false,
            )
        )
        coEvery {
            themesService.getThemes(testContent)
        } returns testResults

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
            val response = client.get("/themes")
            assertEquals(HttpStatusCode.OK, response.status)
        }

        val response = client.get("/themes")

        // Then
        assertEquals(HttpStatusCode.TooManyRequests, response.status)
    }

    @Test
    fun produces404_WHEN_getThemes() = testApplication {
        // Given
        coEvery {
            themesService.getThemes(testContent)
        } returns emptyList()

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        // When
        val response = client.get("/themes")

        // Then
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun produces400_WHEN_getThemes_afterIllegalStateException() = testApplication {
        // Given
        coEvery {
            themesService.getThemes(testContent)
        } throws IllegalStateException()

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        // When
        val response = client.get("/themes")

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces400_WHEN_getThemes_afterJsonConvertException() = testApplication {
        // Given
        coEvery {
            themesService.getThemes(testContent)
        } throws JsonConvertException("Foo")

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        // When
        val response = client.get("/themes")

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces400_WHEN_getThemes_afterIOException() = testApplication {
        // Given
        coEvery {
            themesService.getThemes(testContent)
        } throws IOException()

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        // When
        val response = client.get("/themes")

        // Then
        assertEquals(HttpStatusCode.InternalServerError, response.status)
    }

    @Test
    fun produces200_WHEN_getThemesWithParentThemeId() = testApplication {
        // Given
        val testResults = listOf(
            ThemeListModel(
                id = testThemeId,
                name = "Test Title",
                description = "Test Description",
                iconUrl = "Test Image Url",
                detail = true,
            )
        )
        coEvery {
            themesService.getThemes(testContent, testParentThemeId)
        } returns testResults

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
        val response = client.get("/themes?parentThemeId=$testParentThemeId")
        val results = response.body<List<ThemeListModel>>()

        // Then
        assertEquals(HttpStatusCode.OK, response.status)
        assertContentEquals(testResults, results)
    }

    @Test
    fun produces400_WHEN_getThemesWithParentThemeId_afterIllegalStateException() = testApplication {
        // Given
        val testResults = listOf(
            ThemeListModel(
                id = testThemeId,
                name = "Test Title",
                description = "Test Description",
                iconUrl = "Test Image Url",
                detail = true,
            )
        )
        coEvery {
            themesService.getThemes(testContent, testParentThemeId)
        } returns testResults

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        // When
        val response = client.get("/themes?parentThemeId=Invalid")

        // Then
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun produces404_WHEN_getThemesWithParentThemeId() = testApplication {
        // Given
        val testResults = listOf(
            ThemeListModel(
                id = testThemeId,
                name = "Test Title",
                description = "Test Description",
                iconUrl = "Test Image Url",
                detail = true,
            )
        )
        coEvery {
            themesService.getThemes(testContent, testParentThemeId)
        } returns testResults
        coEvery {
            themesService.getThemes(testContent, testNotExistsThemeId)
        } returns emptyList()

        application {
            module {
                it.declare<Database>(mockk())
                it.declare(themesService)
            }
        }

        // When
        val response = client.get("/themes?parentThemeId=$testNotExistsThemeId")

        // Then
        assertEquals(HttpStatusCode.NotFound, response.status)
    }
}
