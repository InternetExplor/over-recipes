package com.wtduyuwnt.overrecipes.data.model

data class Recipe(
    val id: Int,
    val title: String,
    val categoryKey: String = "",
    val imageUrl: String = "",
    val author: String = "",
    val cuisine: String = "",
    val difficulty: String = "",
    val rating: Float = 0f,
    val reviews: Int = 0,
    val prepMinutes: Int = 0,
    val cookMinutes: Int = 0,
    val servings: Int = 4,
    val calories: Int = 0,
    val summary: String = "",
    val tags: List<String> = emptyList(),
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<CookStep> = emptyList(),
    val tip: String? = null,
    val videoUrl: String? = null
) {
    val totalMinutes: Int get() = prepMinutes + cookMinutes

    val hasRating: Boolean get() = rating > 0f

    val hasVideo: Boolean get() = !videoUrl.isNullOrBlank()
}
