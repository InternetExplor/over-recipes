package com.wtduyuwnt.overrecipes.ui.state

import com.wtduyuwnt.overrecipes.data.model.Recipe

data class FavoritesUiState(
    val recipes: List<Recipe> = emptyList(),
    val favorites: Set<Int> = emptySet(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
) {
    val isEmpty: Boolean
        get() = !isLoading && errorMessage == null && recipes.isEmpty()

    val hasError: Boolean
        get() = !isLoading && errorMessage != null
}
