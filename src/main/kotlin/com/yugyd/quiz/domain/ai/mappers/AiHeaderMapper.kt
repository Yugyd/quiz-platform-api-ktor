package com.yugyd.quiz.domain.ai.mappers

import com.yugyd.quiz.domain.ai.models.AiAuthModel
import io.ktor.http.Headers

internal interface AiHeaderMapper {
    fun map(headers: Headers): AiAuthModel
}
