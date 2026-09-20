package com.wtduyuwnt.overrecipes.data.repository

import com.wtduyuwnt.overrecipes.data.mock.MockCategories
import com.wtduyuwnt.overrecipes.data.mock.MockRecipes
import com.wtduyuwnt.overrecipes.data.model.ALL_CATEGORY_KEY
import com.wtduyuwnt.overrecipes.data.model.Category
import com.wtduyuwnt.overrecipes.data.model.Recipe
import kotlinx.coroutines.delay

private const val FEATURED_RATING = 4.6f

class MockRecipeRepository(
    private val latencyMillis: Long = 250L
) : RecipeRepository {

    override suspend fun getCategories(): Result<List<Category>> = runCatching {
        delay(latencyMillis)
        MockCategories.all
    }

    override suspend fun getRecipes(
        categoryKey: String,
        page: Int,
        perPage: Int
    ): Result<List<Recipe>> = runCatching {
        delay(latencyMillis)
        filterByCategory(categoryKey)
            .drop((page - 1).coerceAtLeast(0) * perPage)
            .take(perPage)
    }

    override suspend fun getRecipe(id: Int): Result<Recipe?> = runCatching {
        delay(latencyMillis)
        MockRecipes.all.firstOrNull { it.id == id }
    }

    override suspend fun getFeatured(): Result<List<Recipe>> = runCatching {
        delay(latencyMillis)
        MockRecipes.all.filter { it.rating >= FEATURED_RATING }
    }

    override suspend fun search(query: String, categoryKey: String): Result<List<Recipe>> =
        runCatching {
            delay(latencyMillis)
            val base = filterByCategory(categoryKey)
            if (query.isBlank()) base
            else {
                val normalized = query.trim().lowercase()
                base.filter { recipe -> recipe.matches(normalized) }
            }
        }

    private fun filterByCategory(categoryKey: String): List<Recipe> =
        if (categoryKey == ALL_CATEGORY_KEY) MockRecipes.all
        else MockRecipes.all.filter { it.categoryKey == categoryKey }

    private fun Recipe.matches(query: String): Boolean =
        title.lowercase().contains(query) ||
            cuisine.lowercase().contains(query) ||
            tags.any { it.lowercase().contains(query) } ||
            ingredients.any { it.name.lowercase().contains(query) }
}
