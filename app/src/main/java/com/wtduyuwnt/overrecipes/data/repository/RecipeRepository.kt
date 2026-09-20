package com.wtduyuwnt.overrecipes.data.repository

import com.wtduyuwnt.overrecipes.data.model.ALL_CATEGORY_KEY
import com.wtduyuwnt.overrecipes.data.model.Category
import com.wtduyuwnt.overrecipes.data.model.Recipe

interface RecipeRepository {
    suspend fun getCategories(): Result<List<Category>>

    suspend fun getRecipes(
        categoryKey: String = ALL_CATEGORY_KEY,
        page: Int = 1,
        perPage: Int = 20
    ): Result<List<Recipe>>

    suspend fun getRecipe(id: Int): Result<Recipe?>

    suspend fun getFeatured(): Result<List<Recipe>>

    suspend fun search(
        query: String,
        categoryKey: String = ALL_CATEGORY_KEY
    ): Result<List<Recipe>>
}
