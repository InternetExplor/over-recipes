package com.wtduyuwnt.overrecipes.data.repository

import com.wtduyuwnt.overrecipes.data.model.ALL_CATEGORY_KEY
import com.wtduyuwnt.overrecipes.data.model.AppLanguage
import com.wtduyuwnt.overrecipes.data.model.Category
import com.wtduyuwnt.overrecipes.data.model.MIN_SEARCH_QUERY_LENGTH
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.data.remote.OshxonaApi
import com.wtduyuwnt.overrecipes.data.remote.mapper.CategoryMapper
import com.wtduyuwnt.overrecipes.data.remote.mapper.RecipeMapper
import kotlinx.coroutines.flow.first

private const val SEARCH_LIMIT = 40
private const val FEATURED_PER_PAGE = 10

class ApiRecipeRepository(
    private val api: OshxonaApi,
    private val settingsRepository: SettingsRepository
) : RecipeRepository {

    override suspend fun getCategories(): Result<List<Category>> = runCatching {
        val language = currentLanguage()
        val remote = CategoryMapper.parseList(api.listCategories(language.code), language)
        listOf(Category(ALL_CATEGORY_KEY, allTitle(language))) + remote
    }

    override suspend fun getRecipes(
        categoryKey: String,
        page: Int,
        perPage: Int
    ): Result<List<Recipe>> = runCatching {
        val language = currentLanguage()
        val payload = if (categoryKey == ALL_CATEGORY_KEY) {
            api.listRecipes(lang = language.code, page = page, perPage = perPage)
        } else {
            api.categoryRecipes(
                key = categoryKey,
                lang = language.code,
                page = page,
                perPage = perPage
            )
        }
        RecipeMapper.parseList(payload)
    }

    override suspend fun getRecipe(id: Int): Result<Recipe?> = runCatching {
        RecipeMapper.parseSingle(api.getRecipe(recipeId = id, lang = currentLanguage().code))
    }

    override suspend fun getFeatured(): Result<List<Recipe>> = runCatching {
        RecipeMapper.parseList(
            api.listRecipes(lang = currentLanguage().code, page = 1, perPage = FEATURED_PER_PAGE)
        )
    }

    override suspend fun search(query: String, categoryKey: String): Result<List<Recipe>> {
        val trimmed = query.trim()
        if (trimmed.length < MIN_SEARCH_QUERY_LENGTH) return getRecipes(categoryKey = categoryKey)
        return runCatching {
            val found = RecipeMapper.parseList(
                api.search(query = trimmed, lang = currentLanguage().code, limit = SEARCH_LIMIT)
            )
            if (categoryKey == ALL_CATEGORY_KEY) found
            else found.filter { it.categoryKey == categoryKey }
        }
    }

    private suspend fun currentLanguage(): AppLanguage = settingsRepository.language.first()

    private fun allTitle(language: AppLanguage): String =
        if (language == AppLanguage.UZBEK) "Hammasi" else "Все"
}
