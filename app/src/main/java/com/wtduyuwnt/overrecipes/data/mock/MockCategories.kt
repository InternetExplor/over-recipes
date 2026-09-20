package com.wtduyuwnt.overrecipes.data.mock

import com.wtduyuwnt.overrecipes.data.model.ALL_CATEGORY_KEY
import com.wtduyuwnt.overrecipes.data.model.Category

object MockCategories {
    val all: List<Category> = listOf(
        Category(ALL_CATEGORY_KEY, "Все"),
        Category("main-dishes", "Вторые блюда"),
        Category("soups", "Супы"),
        Category("dessert", "Десерты")
    )
}
