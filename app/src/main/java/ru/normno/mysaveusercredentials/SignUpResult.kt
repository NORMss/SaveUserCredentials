package ru.normno.mysaveusercredentials

sealed interface SignUpResult {
    data class Success(val username: String) : SignUpResult
    data object Cancelled : SignUpResult
    data object Failure : SignUpResult
}