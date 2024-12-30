package com.yugyd.quiz.domain.ai.mappers

import com.yugyd.quiz.domain.AiHeaders
import com.yugyd.quiz.domain.ai.models.AiAuthModel
import io.ktor.http.Headers

internal class AiHeaderMapperImpl : AiHeaderMapper {

    override fun map(headers: Headers): AiAuthModel {
        return AiAuthModel(
            aiKey = headers[AiHeaders.KEY_HEADER],
            aiFolder = headers[AiHeaders.FOLDER_HEADER],
            aiProvider = headers[AiHeaders.PROVIDER_HEADER],
        )
    }
}
