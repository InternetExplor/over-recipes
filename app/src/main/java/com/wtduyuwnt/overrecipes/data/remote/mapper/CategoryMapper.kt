package com.wtduyuwnt.overrecipes.data.remote.mapper

import com.wtduyuwnt.overrecipes.data.model.AppLanguage
import com.wtduyuwnt.overrecipes.data.model.Category
import com.wtduyuwnt.overrecipes.data.remote.asArrayOrFirstNested
import com.wtduyuwnt.overrecipes.data.remote.objectOrNull
import com.wtduyuwnt.overrecipes.data.remote.primitiveContentOrNull
import com.wtduyuwnt.overrecipes.data.remote.string
import kotlinx.serialization.json.JsonElement

object CategoryMapper {

    fun parseList(element: JsonElement, language: AppLanguage): List<Category> =
        element.asArrayOrFirstNested().mapNotNull { it.toCategoryOrNull(language) }

    private fun JsonElement.toCategoryOrNull(language: AppLanguage): Category? {
        primitiveContentOrNull()?.let { key ->
            return Category(key = key, title = CategoryTitles.localize(key, "", language))
        }

        val obj = objectOrNull() ?: return null
        val key = obj.string("key", "slug", "code", "id") ?: return null
        val remoteTitle = obj.string(
            "title_${language.code}",
            "name_${language.code}",
            "title",
            "name",
            "label"
        ).orEmpty()
        return Category(key = key, title = CategoryTitles.localize(key, remoteTitle, language))
    }
}
