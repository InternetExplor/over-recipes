package com.wtduyuwnt.overrecipes.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wtduyuwnt.overrecipes.ui.screens.favorites.FavoritesScreen
import com.wtduyuwnt.overrecipes.ui.screens.home.HomeScreen
import com.wtduyuwnt.overrecipes.ui.screens.profile.ProfileScreen
import com.wtduyuwnt.overrecipes.ui.screens.recipe.RecipeDetailScreen
import com.wtduyuwnt.overrecipes.ui.screens.recipe.RecipePreviewScreen
import com.wtduyuwnt.overrecipes.ui.screens.auth.LoginScreen
import com.wtduyuwnt.overrecipes.ui.screens.auth.RegisterScreen
import com.wtduyuwnt.overrecipes.ui.screens.welcome.WelcomeScreen
import com.wtduyuwnt.overrecipes.ui.viewmodel.AuthViewModel
import com.wtduyuwnt.overrecipes.ui.viewmodel.FavoritesViewModel
import com.wtduyuwnt.overrecipes.ui.viewmodel.HomeViewModel
import com.wtduyuwnt.overrecipes.ui.viewmodel.ProfileViewModel
import com.wtduyuwnt.overrecipes.ui.viewmodel.RecipeViewModel

private val recipeArguments = listOf(
    navArgument(Destinations.RECIPE_ID_ARG) { type = NavType.IntType }
)

fun NavGraphBuilder.welcomeScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit
) {
    composable(Destinations.WELCOME_ROUTE) {
        WelcomeScreen(onRegister = onRegister, onLogin = onLogin)
    }
}

fun NavGraphBuilder.registerScreen(
    onRegistered: () -> Unit,
    onOpenLogin: () -> Unit,
    onBack: () -> Unit
) {
    composable(Destinations.REGISTER_ROUTE) {
        val viewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        RegisterScreen(
            state = state,
            onNameChange = viewModel::onNameChange,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
            onSubmit = viewModel::onRegister,
            onOpenLogin = onOpenLogin,
            onBack = onBack,
            onRegistered = onRegistered
        )
    }
}

fun NavGraphBuilder.loginScreen(
    onLoggedIn: () -> Unit,
    onOpenRegister: () -> Unit,
    onBack: () -> Unit
) {
    composable(Destinations.LOGIN_ROUTE) {
        val viewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        LoginScreen(
            state = state,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
            onSubmit = viewModel::onLogin,
            onOpenRegister = onOpenRegister,
            onBack = onBack,
            onLoggedIn = onLoggedIn
        )
    }
}

fun NavGraphBuilder.homeScreen(
    contentPadding: PaddingValues,
    onRecipeClick: (Int) -> Unit,
    onProfileClick: () -> Unit
) {
    composable(Destinations.HOME_ROUTE) {
        val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        HomeScreen(
            state = state,
            onQueryChange = viewModel::onQueryChange,
            onCategorySelect = viewModel::onCategorySelect,
            onResetFilters = viewModel::onResetFilters,
            onRetry = viewModel::onRetry,
            onToggleFavorite = viewModel::onToggleFavorite,
            onRecipeClick = onRecipeClick,
            onProfileClick = onProfileClick,
            contentPadding = contentPadding
        )
    }
}

fun NavGraphBuilder.favoritesScreen(
    contentPadding: PaddingValues,
    onRecipeClick: (Int) -> Unit,
    onBrowse: () -> Unit
) {
    composable(Destinations.FAVORITES_ROUTE) {
        val viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory)
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        FavoritesScreen(
            state = state,
            onToggleFavorite = viewModel::onToggleFavorite,
            onRecipeClick = onRecipeClick,
            onBrowse = onBrowse,
            contentPadding = contentPadding
        )
    }
}

fun NavGraphBuilder.profileScreen(
    contentPadding: PaddingValues,
    onSignedOut: () -> Unit
) {
    composable(Destinations.PROFILE_ROUTE) {
        val viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        ProfileScreen(
            state = state,
            onLanguageSelect = viewModel::onLanguageSelect,
            onThemeSelect = viewModel::onThemeSelect,
            onSignOut = { viewModel.onSignOut(onSignedOut) },
            contentPadding = contentPadding
        )
    }
}

fun NavGraphBuilder.recipePreviewScreen(
    onBack: () -> Unit,
    onOpenFull: (Int) -> Unit
) {
    composable(Destinations.PREVIEW_ROUTE, arguments = recipeArguments) {
        val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModel.Factory)
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        RecipePreviewScreen(
            state = state,
            onToggleFavorite = viewModel::onToggleFavorite,
            onBack = onBack,
            onRetry = viewModel::onRetry,
            onOpenFull = { state.recipe?.let { onOpenFull(it.id) } }
        )
    }
}

fun NavGraphBuilder.recipeDetailScreen(onBack: () -> Unit) {
    composable(Destinations.DETAIL_ROUTE, arguments = recipeArguments) {
        val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModel.Factory)
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        RecipeDetailScreen(
            state = state,
            onToggleFavorite = viewModel::onToggleFavorite,
            onServingsChange = viewModel::onServingsChange,
            onStepToggle = viewModel::onStepToggle,
            onRetry = viewModel::onRetry,
            onBack = onBack
        )
    }
}
