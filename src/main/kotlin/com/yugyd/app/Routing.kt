package com.yugyd.app

import com.yugyd.domain.theme.getThemeDetail
import com.yugyd.domain.theme.postThemes
import com.yugyd.domain.theme.service.ThemeService
import io.ktor.server.application.Application
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

internal fun Application.configureRouting() {
    val themeService by inject<ThemeService>()

    routing {
        route("/theme") {
            postThemes(themeService = themeService)

            getThemeDetail(themeService = themeService)
        }
    }
}
