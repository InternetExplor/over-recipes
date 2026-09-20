package com.wtduyuwnt.overrecipes.data.remote

import kotlinx.serialization.json.JsonElement
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OshxonaApi {

    @GET("api/v1/recipes/")
    suspend fun listRecipes(
        @Query("lang") lang: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): JsonElement

    @GET("api/v1/recipes/random")
    suspend fun randomRecipe(
        @Query("lang") lang: String,
        @Query("category") category: String? = null
    ): JsonElement

    @GET("api/v1/recipes/{recipe_id}")
    suspend fun getRecipe(
        @Path("recipe_id") recipeId: Int,
        @Query("lang") lang: String
    ): JsonElement

    @GET("api/v1/categories/")
    suspend fun listCategories(
        @Query("lang") lang: String
    ): JsonElement

    @GET("api/v1/categories/{key}/recipes")
    suspend fun categoryRecipes(
        @Path("key") key: String,
        @Query("lang") lang: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): JsonElement

    @GET("api/v1/search/")
    suspend fun search(
        @Query("q") query: String,
        @Query("lang") lang: String,
        @Query("limit") limit: Int
    ): JsonElement

    @GET("api/v1/search/ingredients")
    suspend fun searchByIngredients(
        @Query("q") query: String,
        @Query("lang") lang: String,
        @Query("limit") limit: Int
    ): JsonElement

    @GET("api/health")
    suspend fun health(): JsonElement
}
