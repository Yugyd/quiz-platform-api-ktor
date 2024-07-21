package com.yugyd.domain.theme

import com.yugyd.domain.ai.factory.AiKeysFactory
import com.yugyd.domain.ai.models.AiAuthModel
import com.yugyd.domain.theme.service.ThemeService
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

internal fun Route.getThemeDetail(
    aiKeysFactory: AiKeysFactory,
    themeService: ThemeService,
) {
    get(path = "/detail/{themeId}") {
        val themeId = call.parameters["themeId"]?.toIntOrNull()
        val recreate = call.parameters["recreate"]?.toBoolean() ?: false

        if (themeId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }

        try {
            val aiAuthModel = AiAuthModel(
                aiKey = call.request.headers["X-Ai-Key"],
                aiFolder = call.request.headers["X-Ai-Folder"],
                aiProvider = call.request.headers["X-Ai-Provider"],
            )

            val aiKeys = aiKeysFactory.create(aiAuthModel)

            val theme = themeService.getThemeDetail(
                id = themeId,
                recreate = recreate,
                aiKeys = aiKeys,
            )

            if (theme == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respond(HttpStatusCode.OK, theme)
        } catch (error: IllegalArgumentException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError)
        } catch (error: IllegalStateException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError)
        } catch (error: JsonConvertException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}
