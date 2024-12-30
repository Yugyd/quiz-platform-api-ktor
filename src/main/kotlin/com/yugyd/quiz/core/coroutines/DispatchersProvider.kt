package com.yugyd.quiz.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

internal interface DispatchersProvider {
    val io: CoroutineDispatcher
}
