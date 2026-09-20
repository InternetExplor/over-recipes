package com.wtduyuwnt.overrecipes.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.data.repository.FavoritesRepository
import com.wtduyuwnt.overrecipes.data.repository.RecipeRepository
import com.wtduyuwnt.overrecipes.di.AppGraph
import com.wtduyuwnt.overrecipes.ui.navigation.Destinations
import com.wtduyuwnt.overrecipes.ui.state.RecipeUiState
import com.wtduyuwnt.overrecipes.ui.state.ScaledIngredient
import com.wtduyuwnt.overrecipes.util.formatAmount
import com.wtduyuwnt.overrecipes.util.toUserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MAX_SERVINGS_FACTOR = 4

private data class RecipeLoad(
    val recipe: Recipe? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class RecipeViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val recipeId: Int = checkNotNull(savedStateHandle[Destinations.RECIPE_ID_ARG]) {
        "recipeId is missing in the navigation arguments"
    }

    private val load = MutableStateFlow(RecipeLoad())
    private val servings = MutableStateFlow(1)
    private val completedSteps = MutableStateFlow(emptySet<Int>())

    val uiState: StateFlow<RecipeUiState> = combine(
        load,
        servings,
        completedSteps,
        favoritesRepository.favorites
    ) { current, servings, steps, favorites ->
        RecipeUiState(
            recipe = current.recipe,
            isLoading = current.isLoading,
            isFavorite = favorites.contains(recipeId),
            servings = servings,
            completedSteps = steps,
            ingredients = scaleIngredients(current.recipe, servings),
            errorMessage = current.errorMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RecipeUiState()
    )

    init {
        loadRecipe()
    }

    fun onRetry() {
        loadRecipe()
    }

    fun onServingsChange(value: Int) {
        val recipe = load.value.recipe ?: return
        servings.value = value.coerceIn(1, recipe.servings * MAX_SERVINGS_FACTOR)
    }

    fun onStepToggle(index: Int) {
        completedSteps.update { steps ->
            if (steps.contains(index)) steps - index else steps + index
        }
    }

    fun onToggleFavorite() {
        viewModelScope.launch { favoritesRepository.toggle(recipeId) }
    }

    private fun loadRecipe() {
        load.value = RecipeLoad(isLoading = true)
        viewModelScope.launch {
            recipeRepository.getRecipe(recipeId).fold(
                onSuccess = { recipe ->
                    load.value = RecipeLoad(recipe = recipe, isLoading = false)
                    servings.value = recipe?.servings ?: 1
                },
                onFailure = { throwable ->
                    load.value = RecipeLoad(
                        isLoading = false,
                        errorMessage = throwable.toUserMessage()
                    )
                }
            )
        }
    }

    private fun scaleIngredients(recipe: Recipe?, servings: Int): List<ScaledIngredient> {
        if (recipe == null) return emptyList()
        val factor = servings.toDouble() / recipe.servings.coerceAtLeast(1)
        return recipe.ingredients.map { ingredient ->
            val amount = ingredient.amount
            ScaledIngredient(
                name = ingredient.name,
                amount = if (amount == null) null else {
                    listOfNotNull(formatAmount(amount * factor), ingredient.unit).joinToString(" ")
                }
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RecipeViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    recipeRepository = AppGraph.recipeRepository,
                    favoritesRepository = AppGraph.favoritesRepository
                )
            }
        }
    }
}
