package com.yugyd.quiz.routes.themes

import com.yugyd.quiz.domain.content.models.ContentModel
import com.yugyd.quiz.domain.theme.service.ThemeService
import io.ktor.http.CacheControl
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.CachingOptions
import io.ktor.serialization.JsonConvertException
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

internal fun Route.getThemes(themeService: ThemeService) {
    get {
        val parentThemeIdParameter = call.parameters["parentThemeId"]

        try {
            val themes = if (parentThemeIdParameter != null) {
                val parentThemeId = parentThemeIdParameter.toInt()

                themeService.getThemes(
                    content = ContentModel.DEFAULT,
                    parentThemeId = parentThemeId,
                )
            } else {
                themeService.getThemes(ContentModel.DEFAULT)
            }

            if (themes.isEmpty()) {
                call.respond(HttpStatusCode.NotFound)
                return@get
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
