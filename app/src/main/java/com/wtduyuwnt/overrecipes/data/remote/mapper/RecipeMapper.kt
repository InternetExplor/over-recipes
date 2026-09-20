package com.wtduyuwnt.overrecipes.data.remote.mapper

import com.wtduyuwnt.overrecipes.data.model.CookStep
import com.wtduyuwnt.overrecipes.data.model.Ingredient
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.data.remote.asArrayOrFirstNested
import com.wtduyuwnt.overrecipes.data.remote.double
import com.wtduyuwnt.overrecipes.data.remote.elementList
import com.wtduyuwnt.overrecipes.data.remote.floatValue
import com.wtduyuwnt.overrecipes.data.remote.int
import com.wtduyuwnt.overrecipes.data.remote.objectOrNull
import com.wtduyuwnt.overrecipes.data.remote.string
import com.wtduyuwnt.overrecipes.data.remote.stringList
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

private const val DEFAULT_SERVINGS = 4

object RecipeMapper {

    fun parseList(element: JsonElement): List<Recipe> =
        element.asArrayOrFirstNested().mapNotNull { it.toRecipeOrNull() }

    fun parseSingle(element: JsonElement): Recipe? {
        val obj = element.objectOrNull() ?: return null
        val payload = obj["recipe"]?.objectOrNull() ?: obj["data"]?.objectOrNull() ?: obj
        return payload.toRecipeOrNull()
    }

    private fun JsonElement.toRecipeOrNull(): Recipe? = objectOrNull()?.toRecipeOrNull()

    private fun JsonObject.toRecipeOrNull(): Recipe? {
        val title = string("title", "name", "recipe_title") ?: return null
        val id = int("id", "recipe_id", "pk") ?: title.hashCode()

        return Recipe(
            id = id,
            title = title,
            categoryKey = string("category_key", "category_slug", "category", "section").orEmpty(),
            imageUrl = string("image", "image_url", "photo", "thumbnail", "cover").orEmpty(),
            author = string("author", "chef", "source").orEmpty(),
            cuisine = string("cuisine", "kitchen", "country").orEmpty(),
            difficulty = string("difficulty", "complexity", "level").orEmpty(),
            rating = floatValue("rating", "score", "stars") ?: 0f,
            reviews = int("reviews", "reviews_count", "votes") ?: 0,
            prepMinutes = int("prep_time", "prep_minutes", "preparation_time") ?: 0,
            cookMinutes = int("cook_time", "cook_minutes", "cooking_time", "time", "total_time") ?: 0,
            servings = int("servings", "portions", "yield") ?: DEFAULT_SERVINGS,
            calories = int("calories", "kcal", "energy") ?: 0,
            summary = string("summary", "description", "short_description", "intro").orEmpty(),
            tags = stringList("tags", "labels", "keywords"),
            ingredients = elementList("ingredients", "ingredient_list", "components")
                .mapNotNull { it.toIngredientOrNull() },
            steps = elementList("steps", "instructions", "directions", "method")
                .mapNotNull { it.toStepOrNull() },
            tip = string("tip", "advice", "note"),
            videoUrl = string(
                "video",
                "video_url",
                "videoUrl",
                "youtube",
                "youtube_url",
                "video_link",
                "embed_url",
                "video_id"
            )
        )
    }

    private fun JsonElement.toIngredientOrNull(): Ingredient? = when (this) {
        is JsonPrimitive -> content.trim().takeIf { it.isNotBlank() }?.let { Ingredient(name = it) }

        is JsonObject -> {
            val name = string("name", "title", "ingredient", "product")
            if (name == null) null
            else Ingredient(
                name = name,
                amount = double("amount", "quantity", "value", "count"),
                unit = string("unit", "measure", "units")
            )
        }

        else -> null
    }

    private fun JsonElement.toStepOrNull(): CookStep? = when (this) {
        is JsonPrimitive -> content.trim().takeIf { it.isNotBlank() }?.let { CookStep(text = it) }

        is JsonObject -> {
            val text = string("text", "step", "description", "instruction", "content")
            if (text == null) null
            else CookStep(text = text, minutes = int("minutes", "time", "duration"))
        }

        else -> null
    }
}
