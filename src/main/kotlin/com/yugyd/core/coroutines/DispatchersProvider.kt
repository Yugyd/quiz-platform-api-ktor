package com.yugyd.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

internal interface DispatchersProvider {
    val io: CoroutineDispatcher
}
