package ru.normno.mysaveusercredentials.login

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import ru.normno.mysaveusercredentials.AccountManager

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    onLoggedIn: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }

    LaunchedEffect(true) {
        val result = accountManager.signIn()
        onAction(LoginAction.OnSignIn(result))
    }

    LaunchedEffect(state.loggedInUser) {
        if (state.loggedInUser != null) {
            onLoggedIn(state.loggedInUser)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = state.username,
            onValueChange = {
                onAction(LoginAction.OnUsernameChange(it))
            },
            placeholder = {
                Text(
                    text = "Username"
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
        TextField(
            value = state.password,
            onValueChange = {
                onAction(LoginAction.OnPasswordChange(it))
            },
            placeholder = {
                Text(
                    text = "Password"
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Register",
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp),
            )
            Switch(
                checked = state.isRegister,
                onCheckedChange = {
                    onAction(LoginAction.OnToggleIsRegister)
                },
            )
        }
        if (state.errorMessage != null) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error,
            )
        }
        Button(
            onClick = {
                scope.launch {
                    if (state.isRegister) {
                        val result = accountManager.signUp(
                            username = state.username,
                            password = state.password,
                        )
                        onAction(LoginAction.OnSignUp(result = result))
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = if (state.isRegister) "Register" else "Sign Up"
            )
        }
    }
}