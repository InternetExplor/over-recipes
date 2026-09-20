package com.wtduyuwnt.overrecipes.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.navigateToRegister() {
    navigate(Destinations.REGISTER_ROUTE)
}

fun NavController.navigateToLogin() {
    navigate(Destinations.LOGIN_ROUTE)
}

fun NavController.navigateToHomeAfterAuth() {
    navigate(Destinations.HOME_ROUTE) {
        popUpTo(Destinations.WELCOME_ROUTE) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavController.navigateToWelcomeAfterSignOut() {
    navigate(Destinations.WELCOME_ROUTE) {
        popUpTo(Destinations.HOME_ROUTE) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavController.navigateToPreview(recipeId: Int) {
    navigate(Destinations.preview(recipeId))
}

fun NavController.navigateToDetail(recipeId: Int) {
    navigate(Destinations.detail(recipeId))
}

fun NavController.navigateToTab(route: String) {
    navigate(route) {
        popUpToHome()
        launchSingleTop = true
        restoreState = true
    }
}

private fun NavOptionsBuilder.popUpToHome() {
    popUpTo(Destinations.HOME_ROUTE) { saveState = true }
}
