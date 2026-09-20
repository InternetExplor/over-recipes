package com.wtduyuwnt.overrecipes.ui.screens.auth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.screens.auth.components.AuthScreenScaffold
import com.wtduyuwnt.overrecipes.ui.screens.auth.components.AuthTextField
import com.wtduyuwnt.overrecipes.ui.state.AuthUiState

@Composable
fun LoginScreen(
    state: AuthUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmit: () -> Unit,
    onOpenRegister: () -> Unit,
    onBack: () -> Unit,
    onLoggedIn: () -> Unit
) {
    LaunchedEffect(state.isDone) {
        if (state.isDone) onLoggedIn()
    }

    AuthScreenScaffold(
        title = "С возвращением",
        subtitle = "Войдите в локальный аккаунт, чтобы вернуться к своим рецептам.",
        onBack = onBack
    ) {
        AuthTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = "Почта",
            keyboardType = KeyboardType.Email
        )
        Spacer(Modifier.height(14.dp))
        AuthTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = "Пароль",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            isPassword = true,
            passwordVisible = state.passwordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility
        )

        if (state.errorMessage != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = state.errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(28.dp))
        Button(
            onClick = onSubmit,
            enabled = !state.isSubmitting,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            if (state.isSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Войти", style = MaterialTheme.typography.labelLarge)
            }
        }
        Spacer(Modifier.height(4.dp))
        TextButton(
            onClick = onOpenRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Создать новый аккаунт")
        }
    }
}
