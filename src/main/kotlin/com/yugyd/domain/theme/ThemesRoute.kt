package com.yugyd.domain.theme

import com.yugyd.domain.content.models.ContentModel
import com.yugyd.domain.theme.models.list.ThemesRequestModel
import com.yugyd.domain.theme.service.ThemeService
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

internal fun Route.postThemes(themeService: ThemeService) {
    post(path = "/themes") {
        val themeRequest = call.receive<ThemesRequestModel>()

        if (ContentModel.entries.firstOrNull { it.serverValue == themeRequest.content } == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        try {
            val contentModel = ContentModel.entries.first { it.serverValue == themeRequest.content }
            val parentThemeId = themeRequest.parentThemeId

            val themes = if (parentThemeId != null) {
                themeService.getThemes(
                    content = contentModel,
                    parentThemeId = parentThemeId,
                )
            } else {
                themeService.getThemes(contentModel)
            }

            if (themes.isEmpty()) {
                call.respond(HttpStatusCode.NotFound)
                return@post
            }

            call.respond(HttpStatusCode.OK, themes)
        } catch (error: IllegalArgumentException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        } catch (error: IllegalStateException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        } catch (error: JsonConvertException) {
            error.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
