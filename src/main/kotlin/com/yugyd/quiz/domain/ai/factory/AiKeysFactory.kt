package com.yugyd.quiz.domain.ai.factory

import com.yugyd.quiz.data.themes.AiInitialException
import com.yugyd.quiz.domain.ai.models.AiAuthModel
import com.yugyd.quiz.domain.ai.models.AiKeysModel

internal interface AiKeysFactory {

    @Throws(AiInitialException::class)
    fun create(aiAuthModel: AiAuthModel): AiKeysModel?
}
