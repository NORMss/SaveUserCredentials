package ru.normno.mysaveusercredentials.login

data class LoginState(
    val loggedInUser: String? = null,
    val username: String = "user",
    val password: String = "password",
    val errorMessage: String? = null,
    val isRegister: Boolean = false,
)
