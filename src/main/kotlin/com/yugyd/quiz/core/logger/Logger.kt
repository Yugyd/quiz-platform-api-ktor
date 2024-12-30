package com.yugyd.quiz.core.logger

interface Logger {
    fun applicationDebug(error: Throwable)
    fun applicationDebug(tag: String, msg: String)
    fun debug(error: Throwable)
    fun debug(tag: String, msg: String)
    fun error(error: Throwable)
}
