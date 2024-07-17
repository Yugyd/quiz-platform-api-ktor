package com.yugyd.data.ai.yandex.entities.request

import com.yugyd.data.ai.yandex.entities.MessageDao
import kotlinx.serialization.Serializable

@Serializable
internal data class CompletionRequest(
    val modelUri: String,
    val completionOptions: CompletionOptionsDao,
    val messages: List<MessageDao>,
)
