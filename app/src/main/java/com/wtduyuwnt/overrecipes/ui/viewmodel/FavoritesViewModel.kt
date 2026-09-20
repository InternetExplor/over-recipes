package com.wtduyuwnt.overrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.data.repository.FavoritesRepository
import com.wtduyuwnt.overrecipes.data.repository.RecipeRepository
import com.wtduyuwnt.overrecipes.di.AppGraph
import com.wtduyuwnt.overrecipes.ui.state.FavoritesUiState
import com.wtduyuwnt.overrecipes.util.toUserMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModel(
    private val recipeRepository: RecipeRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> = favoritesRepository.favorites
        .flatMapLatest { favorites ->
            flow {
                emit(FavoritesUiState(favorites = favorites, isLoading = true))
                emit(loadRecipes(favorites))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritesUiState()
        )

    fun onToggleFavorite(recipeId: Int) {
        viewModelScope.launch { favoritesRepository.toggle(recipeId) }
    }

    private suspend fun loadRecipes(favorites: Set<Int>): FavoritesUiState {
        if (favorites.isEmpty()) {
            return FavoritesUiState(favorites = favorites, isLoading = false)
        }
        val loaded = mutableListOf<Recipe>()
        var error: String? = null
        favorites.forEach { id ->
            recipeRepository.getRecipe(id).fold(
                onSuccess = { recipe -> recipe?.let(loaded::add) },
                onFailure = { throwable -> error = throwable.toUserMessage() }
            )
        }
        return FavoritesUiState(
            recipes = loaded.sortedBy(Recipe::title),
            favorites = favorites,
            isLoading = false,
            errorMessage = error.takeIf { loaded.isEmpty() }
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                FavoritesViewModel(AppGraph.recipeRepository, AppGraph.favoritesRepository)
            }
        }
    }
}
