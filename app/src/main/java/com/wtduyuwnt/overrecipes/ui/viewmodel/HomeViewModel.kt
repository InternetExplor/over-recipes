package com.wtduyuwnt.overrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wtduyuwnt.overrecipes.data.model.ALL_CATEGORY_KEY
import com.wtduyuwnt.overrecipes.data.model.AppLanguage
import com.wtduyuwnt.overrecipes.data.model.Category
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.data.repository.FavoritesRepository
import com.wtduyuwnt.overrecipes.data.repository.RecipeRepository
import com.wtduyuwnt.overrecipes.data.repository.SettingsRepository
import com.wtduyuwnt.overrecipes.di.AppGraph
import com.wtduyuwnt.overrecipes.ui.state.HomeUiState
import com.wtduyuwnt.overrecipes.util.toUserMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val SEARCH_DEBOUNCE_MILLIS = 350L

private data class Query(
    val text: String = "",
    val category: String = ALL_CATEGORY_KEY,
    val language: AppLanguage = AppLanguage.RUSSIAN,
    val reloadToken: Int = 0
)

private data class StaticContent(
    val categories: List<Category> = emptyList(),
    val featured: List<Recipe> = emptyList()
)

private data class SearchResult(
    val recipes: List<Recipe>? = null,
    val errorMessage: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class HomeViewModel(
    private val recipeRepository: RecipeRepository,
    private val favoritesRepository: FavoritesRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val query = MutableStateFlow(Query())
    private val staticContent = MutableStateFlow(StaticContent())

    private val results = query
        .debounce { if (it.text.isBlank()) 0L else SEARCH_DEBOUNCE_MILLIS }
        .distinctUntilChanged()
        .flatMapLatest { current ->
            flow {
                emit(SearchResult())
                val outcome = recipeRepository.search(current.text, current.category)
                emit(
                    outcome.fold(
                        onSuccess = { SearchResult(recipes = it) },
                        onFailure = { SearchResult(errorMessage = it.toUserMessage()) }
                    )
                )
            }
        }

    val uiState: StateFlow<HomeUiState> = combine(
        query,
        staticContent,
        results,
        favoritesRepository.favorites
    ) { current, content, result, favorites ->
        HomeUiState(
            query = current.text,
            selectedCategory = current.category,
            categories = content.categories,
            featured = content.featured,
            recipes = result.recipes.orEmpty(),
            favorites = favorites,
            isLoading = result.recipes == null && result.errorMessage == null,
            errorMessage = result.errorMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )

    init {
        viewModelScope.launch {
            settingsRepository.language.collect { language ->
                query.update { it.copy(language = language) }
                loadStaticContent()
            }
        }
    }

    fun onQueryChange(text: String) {
        query.update { it.copy(text = text) }
    }

    fun onCategorySelect(categoryKey: String) {
        query.update { it.copy(category = categoryKey) }
    }

    fun onResetFilters() {
        query.update { Query(language = it.language, reloadToken = it.reloadToken) }
    }

    fun onRetry() {
        loadStaticContent()
        query.update { it.copy(reloadToken = it.reloadToken + 1) }
    }

    fun onToggleFavorite(recipeId: Int) {
        viewModelScope.launch { favoritesRepository.toggle(recipeId) }
    }

    private fun loadStaticContent() {
        viewModelScope.launch {
            val categories = recipeRepository.getCategories().getOrElse { emptyList() }
            val featured = recipeRepository.getFeatured().getOrElse { emptyList() }
            staticContent.value = StaticContent(categories = categories, featured = featured)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    recipeRepository = AppGraph.recipeRepository,
                    favoritesRepository = AppGraph.favoritesRepository,
                    settingsRepository = AppGraph.settingsRepository
                )
            }
        }
    }
}
