package com.yugyd.quiz.routes.themes

import com.yugyd.quiz.data.themes.AiInitialException
import com.yugyd.quiz.domain.ai.exceptions.InvalidAiCredentialException
import com.yugyd.quiz.domain.ai.factory.AiKeysFactory
import com.yugyd.quiz.domain.ai.mappers.AiHeaderMapper
import com.yugyd.quiz.domain.theme.service.ThemeService
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.koin.mp.KoinPlatform.getKoin

internal fun Route.getThemeDetail(
    themeService: ThemeService,
) {
    get(path = "/{themeId}") {
        val themeId = call.parameters["themeId"]?.toIntOrNull()
        val recreate = call.parameters["recreate"]?.toBoolean() ?: false

        if (themeId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }

        try {
            val aiHeaderMapper = getKoin().get<AiHeaderMapper>()
            val aiAuthModel = aiHeaderMapper.map(call.request.headers)
            val aiKeysFactory = getKoin().get<AiKeysFactory>()
            val aiKeys = aiKeysFactory.create(aiAuthModel)

            if (aiKeys == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ai headers")
                return@get
            }

            val theme = themeService.getThemeDetail(
                id = themeId,
                recreate = recreate,
                aiKeys = aiKeys,
            )

            if (theme == null || theme.content.isBlank()) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respond(HttpStatusCode.OK, theme)
        } catch (error: AiInitialException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest, error.message.orEmpty())
        } catch (error: InvalidAiCredentialException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.Unauthorized)
        } catch (error: IllegalArgumentException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        } catch (error: JsonConvertException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
