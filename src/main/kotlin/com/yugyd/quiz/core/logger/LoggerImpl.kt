package com.yugyd.quiz.core.logger

import io.ktor.util.logging.KtorSimpleLogger
import io.ktor.util.logging.Logger as KtorLogger

private val QUIZ_SCOPED_LOGGER = KtorSimpleLogger("com.yugyd.quiz")

internal class LoggerImpl(
    private val globalLogger: KtorLogger,
    private val quizLogger: KtorLogger = QUIZ_SCOPED_LOGGER,
) : Logger {

    override fun debug(error: Throwable) {
        error.printStackTrace()
        quizLogger.debug(error.localizedMessage)
    }

    override fun debug(tag: String, msg: String) {
        quizLogger.debug("$tag: $msg")
    }

    override fun error(error: Throwable) {
        error.printStackTrace()
        quizLogger.error(error.localizedMessage)
    }

    override fun applicationDebug(error: Throwable) {
        error.printStackTrace()
        globalLogger.debug(error.localizedMessage)
    }

    override fun applicationDebug(tag: String, msg: String) {
        globalLogger.debug("$tag: $msg")
    }
}
