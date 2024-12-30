package com.yugyd.quiz

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.ratelimit.RateLimit
import io.ktor.server.plugins.ratelimit.RateLimitName
import kotlin.time.Duration.Companion.seconds

const val AI_RATE_LIMIT_QUALIFIER = "ai-rate-limit"

internal fun Application.configureRateLimit() {
    install(RateLimit) {
        register(
            RateLimitName(AI_RATE_LIMIT_QUALIFIER),
        ) {
            rateLimiter(
                limit = 100,
                refillPeriod = 60.seconds,
            )
        }

        global {
            rateLimiter(
                limit = 1000,
                refillPeriod = 60.seconds,
            )
        }
    }
}
