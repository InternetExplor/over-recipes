package com.wtduyuwnt.overrecipes.ui.state

import com.wtduyuwnt.overrecipes.data.model.Recipe

data class RecipeUiState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val servings: Int = 1,
    val completedSteps: Set<Int> = emptySet(),
    val ingredients: List<ScaledIngredient> = emptyList(),
    val errorMessage: String? = null
) {
    val hasError: Boolean
        get() = !isLoading && errorMessage != null

    val isNotFound: Boolean
        get() = !isLoading && errorMessage == null && recipe == null

    val stepsCount: Int
        get() = recipe?.steps?.size ?: 0

    val progress: Float
        get() = if (stepsCount == 0) 0f else completedSteps.size.toFloat() / stepsCount
}
