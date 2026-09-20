package com.wtduyuwnt.overrecipes.data.repository

import com.wtduyuwnt.overrecipes.data.local.AccountDataStore
import com.wtduyuwnt.overrecipes.data.model.AuthState
import kotlinx.coroutines.flow.Flow

const val MIN_PASSWORD_LENGTH = 6

private val EMAIL_PATTERN = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$")

class AccountRepository(
    private val dataStore: AccountDataStore
) {
    val authState: Flow<AuthState> = dataStore.authState

    suspend fun hasAccount(): Boolean = dataStore.hasAccount()

    suspend fun savedEmail(): String? = dataStore.savedEmail()

    suspend fun register(name: String, email: String, password: String): Result<Unit> {
        val trimmedName = name.trim()
        val trimmedEmail = email.trim()

        return when {
            trimmedName.length < 2 -> failure("Имя должно быть не короче 2 символов")
            !EMAIL_PATTERN.matches(trimmedEmail) -> failure("Проверьте адрес почты")
            password.length < MIN_PASSWORD_LENGTH ->
                failure("Пароль должен быть не короче $MIN_PASSWORD_LENGTH символов")

            else -> {
                dataStore.register(trimmedName, trimmedEmail, password)
                Result.success(Unit)
            }
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isEmpty() || password.isEmpty()) {
            return failure("Заполните оба поля")
        }
        if (!hasAccount()) {
            return failure("На этом устройстве ещё нет аккаунта")
        }
        return if (dataStore.login(trimmedEmail, password)) Result.success(Unit)
        else failure("Неверная почта или пароль")
    }

    suspend fun signOut() {
        dataStore.signOut()
    }

    private fun failure(message: String): Result<Unit> = Result.failure(IllegalArgumentException(message))
}
