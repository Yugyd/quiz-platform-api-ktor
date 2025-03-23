package com.yugyd.quiz.core.coroutines

import kotlinx.coroutines.CancellationException

@SinceKotlin("1.3")
inline fun <R> runCatchingWithoutCancellation(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (throwable: CancellationException) {
        throw throwable
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
