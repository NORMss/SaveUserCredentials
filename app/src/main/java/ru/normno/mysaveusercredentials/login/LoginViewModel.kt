package ru.normno.mysaveusercredentials.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.normno.mysaveusercredentials.SignInResult
import ru.normno.mysaveusercredentials.SignUpResult

class LoginViewModel : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnPasswordChange -> {
                state = state.copy(password = action.password)
            }

            is LoginAction.OnSignUp -> {
                when (action.result) {
                    SignUpResult.Cancelled -> {
                        state = state.copy(
                            errorMessage = "Sign Up was cancelled."
                        )
                    }

                    SignUpResult.Failure -> {
                        state = state.copy(
                            errorMessage = "Sign Up failed."
                        )
                    }

                    is SignUpResult.Success -> {
                        state = state.copy(
                            loggedInUser = action.result.username
                        )
                    }
                }
            }

            is LoginAction.OnSignIn -> {
                when (action.result) {
                    SignInResult.Cancelled -> {
                        state = state.copy(
                            errorMessage = "Sign In was cancelled."
                        )
                    }

                    SignInResult.Failure -> {
                        state = state.copy(
                            errorMessage = "Sign In failed."
                        )
                    }

                    is SignInResult.Success -> {
                        state = state.copy(
                            loggedInUser = action.result.username
                        )
                    }

                    SignInResult.NoCredentials -> {
                        state = state.copy(
                            errorMessage = "No credentials were set up."
                        )
                    }
                }
            }

            LoginAction.OnToggleIsRegister -> {
                state = state.copy(isRegister = !state.isRegister)
            }

            is LoginAction.OnUsernameChange -> {
                state = state.copy(username = action.username)
            }
        }
    }
}