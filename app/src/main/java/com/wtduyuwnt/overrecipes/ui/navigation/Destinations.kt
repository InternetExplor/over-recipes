package com.wtduyuwnt.overrecipes.ui.navigation

object Destinations {
    const val RECIPE_ID_ARG = "recipeId"

    const val WELCOME_ROUTE = "welcome"
    const val REGISTER_ROUTE = "register"
    const val LOGIN_ROUTE = "login"
    const val HOME_ROUTE = "home"
    const val FAVORITES_ROUTE = "favorites"
    const val PROFILE_ROUTE = "profile"

    const val PREVIEW_ROUTE = "preview/{$RECIPE_ID_ARG}"
    const val DETAIL_ROUTE = "detail/{$RECIPE_ID_ARG}"

    fun preview(recipeId: Int): String = "preview/$recipeId"
    fun detail(recipeId: Int): String = "detail/$recipeId"
}
