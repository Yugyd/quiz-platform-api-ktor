package com.yugyd.domain.ai.factory

import com.yugyd.domain.ai.models.AiAuthModel
import com.yugyd.domain.ai.models.AiKeysModel

internal interface AiKeysFactory {
    fun create(aiAuthModel: AiAuthModel): AiKeysModel?
}
