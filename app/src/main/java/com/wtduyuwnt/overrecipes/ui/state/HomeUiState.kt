package com.wtduyuwnt.overrecipes.ui.state

import com.wtduyuwnt.overrecipes.data.model.ALL_CATEGORY_KEY
import com.wtduyuwnt.overrecipes.data.model.Category
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.data.model.MIN_SEARCH_QUERY_LENGTH

data class HomeUiState(
    val query: String = "",
    val selectedCategory: String = ALL_CATEGORY_KEY,
    val categories: List<Category> = emptyList(),
    val featured: List<Recipe> = emptyList(),
    val recipes: List<Recipe> = emptyList(),
    val favorites: Set<Int> = emptySet(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
) {
    val isSearching: Boolean
        get() = query.trim().length >= MIN_SEARCH_QUERY_LENGTH

    val queryTooShort: Boolean
        get() = query.isNotBlank() && query.trim().length < MIN_SEARCH_QUERY_LENGTH

    val showFeatured: Boolean
        get() = query.isBlank() && selectedCategory == ALL_CATEGORY_KEY && featured.isNotEmpty()

    val isEmpty: Boolean
        get() = !isLoading && errorMessage == null && recipes.isEmpty()

    val hasError: Boolean
        get() = !isLoading && errorMessage != null
}
