package com.wtduyuwnt.overrecipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wtduyuwnt.overrecipes.data.model.ALL_CATEGORY_KEY
import com.wtduyuwnt.overrecipes.data.model.AppLanguage
import com.wtduyuwnt.overrecipes.data.model.AuthState
import com.wtduyuwnt.overrecipes.data.model.ThemeMode
import com.wtduyuwnt.overrecipes.data.repository.AccountRepository
import com.wtduyuwnt.overrecipes.data.repository.FavoritesRepository
import com.wtduyuwnt.overrecipes.data.repository.RecipeRepository
import com.wtduyuwnt.overrecipes.data.repository.SettingsRepository
import com.wtduyuwnt.overrecipes.di.AppGraph
import com.wtduyuwnt.overrecipes.ui.state.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val recipeRepository: RecipeRepository,
    private val settingsRepository: SettingsRepository,
    private val accountRepository: AccountRepository,
    favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val categoriesCount = MutableStateFlow(0)

    val uiState: StateFlow<ProfileUiState> = combine(
        categoriesCount,
        favoritesRepository.favorites,
        settingsRepository.language,
        settingsRepository.themeMode,
        accountRepository.authState
    ) { categories, favorites, language, theme, auth ->
        val account = (auth as? AuthState.SignedIn)?.account
        ProfileUiState(
            name = account?.name ?: "Гость",
            email = account?.email.orEmpty(),
            categoriesCount = categories,
            favoritesCount = favorites.size,
            language = language,
            themeMode = theme
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProfileUiState()
    )

    init {
        viewModelScope.launch {
            settingsRepository.language.collect { reloadCategories() }
        }
    }

    fun onLanguageSelect(language: AppLanguage) {
        viewModelScope.launch { settingsRepository.setLanguage(language) }
    }

    fun onThemeSelect(mode: ThemeMode) {
        viewModelScope.launch { settingsRepository.setThemeMode(mode) }
    }

    fun onSignOut(onSignedOut: () -> Unit) {
        viewModelScope.launch {
            accountRepository.signOut()
            onSignedOut()
        }
    }

    private suspend fun reloadCategories() {
        categoriesCount.value = recipeRepository.getCategories()
            .getOrElse { emptyList() }
            .count { it.key != ALL_CATEGORY_KEY }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ProfileViewModel(
                    recipeRepository = AppGraph.recipeRepository,
                    settingsRepository = AppGraph.settingsRepository,
                    accountRepository = AppGraph.accountRepository,
                    favoritesRepository = AppGraph.favoritesRepository
                )
            }
        }
    }
}
