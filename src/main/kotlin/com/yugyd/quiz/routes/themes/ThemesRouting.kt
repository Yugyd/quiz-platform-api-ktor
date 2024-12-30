package com.yugyd.quiz.routes.themes

import com.yugyd.quiz.domain.theme.service.ThemeService
import io.ktor.http.CacheControl
import io.ktor.http.content.CachingOptions
import io.ktor.server.application.Application
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

internal fun Application.configureThemesRouting() {
    val themeService by inject<ThemeService>()

    routing {
        route("/themes") {
            install(CachingHeaders) {
                options { call, _ ->
                    when {
                        call.request.queryParameters["recreate"].toBoolean() -> {
                            CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 60))
                        }

                        else -> {
                            CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 3600))
                        }
                    }
                }
            }

            getThemes(themeService = themeService)

            getThemeDetail(themeService = themeService)
        }
    }
}
