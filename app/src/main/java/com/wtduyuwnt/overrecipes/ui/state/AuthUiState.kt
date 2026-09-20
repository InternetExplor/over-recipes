package com.wtduyuwnt.overrecipes.ui.state

data class AuthUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null,
    val isDone: Boolean = false
)
