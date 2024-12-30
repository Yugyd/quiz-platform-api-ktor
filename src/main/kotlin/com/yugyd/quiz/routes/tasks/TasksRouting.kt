package com.yugyd.quiz.routes.tasks

import com.yugyd.quiz.AI_RATE_LIMIT_QUALIFIER
import com.yugyd.quiz.domain.tasks.service.TasksService
import io.ktor.http.CacheControl
import io.ktor.http.content.CachingOptions
import io.ktor.server.application.Application
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.plugins.ratelimit.RateLimitName
import io.ktor.server.plugins.ratelimit.rateLimit
import io.ktor.server.request.uri
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

internal fun Application.configureTasksRouting() {
    val tasksService by inject<TasksService>()

    routing {
        rateLimit(RateLimitName(AI_RATE_LIMIT_QUALIFIER)) {
            route("/tasks") {
                install(CachingHeaders) {
                    options { call, _ ->
                        when {
                            call.request.uri.contains("verification") -> {
                                CachingOptions(CacheControl.NoCache(visibility = CacheControl.Visibility.Public))
                            }

                            else -> {
                                CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 60))
                            }
                        }
                    }

                    getTasks(tasksService = tasksService)

                    postTasksVerification(tasksService = tasksService)
                }
            }
        }
    }
}
