package com.wtduyuwnt.overrecipes.data.model

sealed interface AuthState {
    data object Loading : AuthState
    data object SignedOut : AuthState
    data class SignedIn(val account: UserAccount) : AuthState
}
