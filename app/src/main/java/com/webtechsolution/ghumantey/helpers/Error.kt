package com.webtechsolution.ghumantey.helpers

abstract class Error {
    abstract val errorType: ErrorType
    abstract val message: String
}

enum class ErrorType {
    UnAuth,
    SERVER_ERROR,
    OTHER
}

data class HandledError(
    override val message: String,
    override val errorType: ErrorType = ErrorType.OTHER
) : Error()