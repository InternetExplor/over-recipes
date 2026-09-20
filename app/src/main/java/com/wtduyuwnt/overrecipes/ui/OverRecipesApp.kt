package com.wtduyuwnt.overrecipes.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wtduyuwnt.overrecipes.data.model.AuthState
import com.wtduyuwnt.overrecipes.di.AppGraph
import com.wtduyuwnt.overrecipes.ui.navigation.Destinations
import com.wtduyuwnt.overrecipes.ui.navigation.favoritesScreen
import com.wtduyuwnt.overrecipes.ui.navigation.homeScreen
import com.wtduyuwnt.overrecipes.ui.navigation.loginScreen
import com.wtduyuwnt.overrecipes.ui.navigation.navigateToDetail
import com.wtduyuwnt.overrecipes.ui.navigation.navigateToHomeAfterAuth
import com.wtduyuwnt.overrecipes.ui.navigation.navigateToLogin
import com.wtduyuwnt.overrecipes.ui.navigation.navigateToPreview
import com.wtduyuwnt.overrecipes.ui.navigation.navigateToRegister
import com.wtduyuwnt.overrecipes.ui.navigation.navigateToTab
import com.wtduyuwnt.overrecipes.ui.navigation.navigateToWelcomeAfterSignOut
import com.wtduyuwnt.overrecipes.ui.navigation.profileScreen
import com.wtduyuwnt.overrecipes.ui.navigation.recipeDetailScreen
import com.wtduyuwnt.overrecipes.ui.navigation.recipePreviewScreen
import com.wtduyuwnt.overrecipes.ui.navigation.registerScreen
import com.wtduyuwnt.overrecipes.ui.navigation.welcomeScreen

private data class BottomDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomDestinations = listOf(
    BottomDestination(Destinations.HOME_ROUTE, "Рецепты", Icons.Filled.Home),
    BottomDestination(Destinations.FAVORITES_ROUTE, "Избранное", Icons.Filled.Favorite),
    BottomDestination(Destinations.PROFILE_ROUTE, "Профиль", Icons.Filled.Person)
)

@Composable
fun OverRecipesApp() {
    val authState by AppGraph.accountRepository.authState
        .collectAsStateWithLifecycle(initialValue = AuthState.Loading)

    if (authState == AuthState.Loading) {
        SplashScreen()
        return
    }

    val startDestination = rememberSaveable {
        if (authState is AuthState.SignedIn) Destinations.HOME_ROUTE else Destinations.WELCOME_ROUTE
    }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = bottomDestinations.any { it.route == currentRoute }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                    bottomDestinations.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = { navController.navigateToTab(destination.route) },
                            icon = { Icon(destination.icon, contentDescription = destination.label) },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize(),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(320)
                ) + fadeIn(tween(220))
            },
            exitTransition = { fadeOut(tween(160)) },
            popEnterTransition = { fadeIn(tween(220)) },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(320)
                ) + fadeOut(tween(220))
            }
        ) {
            welcomeScreen(
                onRegister = { navController.navigateToRegister() },
                onLogin = { navController.navigateToLogin() }
            )
            registerScreen(
                onRegistered = { navController.navigateToHomeAfterAuth() },
                onOpenLogin = { navController.navigateToLogin() },
                onBack = { navController.popBackStack() }
            )
            loginScreen(
                onLoggedIn = { navController.navigateToHomeAfterAuth() },
                onOpenRegister = { navController.navigateToRegister() },
                onBack = { navController.popBackStack() }
            )
            homeScreen(
                contentPadding = padding,
                onRecipeClick = navController::navigateToPreview,
                onProfileClick = { navController.navigateToTab(Destinations.PROFILE_ROUTE) }
            )
            favoritesScreen(
                contentPadding = padding,
                onRecipeClick = navController::navigateToPreview,
                onBrowse = { navController.navigateToTab(Destinations.HOME_ROUTE) }
            )
            profileScreen(
                contentPadding = padding,
                onSignedOut = { navController.navigateToWelcomeAfterSignOut() }
            )
            recipePreviewScreen(
                onBack = { navController.popBackStack() },
                onOpenFull = navController::navigateToDetail
            )
            recipeDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}
