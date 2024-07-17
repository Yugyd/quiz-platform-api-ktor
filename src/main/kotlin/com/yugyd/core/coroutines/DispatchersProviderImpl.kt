package com.yugyd.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class DispatchersProviderImpl : DispatchersProvider {
    override val io: CoroutineDispatcher = Dispatchers.IO
}
