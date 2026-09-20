package com.wtduyuwnt.overrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wtduyuwnt.overrecipes.data.repository.AccountRepository
import com.wtduyuwnt.overrecipes.di.AppGraph
import com.wtduyuwnt.overrecipes.ui.state.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val state = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = state.asStateFlow()

    init {
        viewModelScope.launch {
            accountRepository.savedEmail()?.let { email ->
                state.update { it.copy(email = email) }
            }
        }
    }

    fun onNameChange(value: String) = state.update { it.copy(name = value, errorMessage = null) }

    fun onEmailChange(value: String) = state.update { it.copy(email = value, errorMessage = null) }

    fun onPasswordChange(value: String) =
        state.update { it.copy(password = value, errorMessage = null) }

    fun onTogglePasswordVisibility() =
        state.update { it.copy(passwordVisible = !it.passwordVisible) }

    fun onRegister() = submit { current ->
        accountRepository.register(
            name = current.name,
            email = current.email,
            password = current.password
        )
    }

    fun onLogin() = submit { current ->
        accountRepository.login(email = current.email, password = current.password)
    }

    private fun submit(action: suspend (AuthUiState) -> Result<Unit>) {
        if (state.value.isSubmitting) return
        state.update { it.copy(isSubmitting = true, errorMessage = null) }
        viewModelScope.launch {
            action(state.value).fold(
                onSuccess = {
                    state.update { it.copy(isSubmitting = false, isDone = true) }
                },
                onFailure = { throwable ->
                    state.update {
                        it.copy(
                            isSubmitting = false,
                            errorMessage = throwable.message ?: "Не удалось выполнить вход"
                        )
                    }
                }
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { AuthViewModel(AppGraph.accountRepository) }
        }
    }
}
