package com.wtduyuwnt.overrecipes.data.mock

import com.wtduyuwnt.overrecipes.data.model.CookStep
import com.wtduyuwnt.overrecipes.data.model.Ingredient
import com.wtduyuwnt.overrecipes.data.model.Recipe

internal val dessertRecipes: List<Recipe> = listOf(
    Recipe(
        id = 11,
        title = "Шоколадные кукис",
        categoryKey = "dessert",
        imageUrl = "${ZiraImages.BASE}/2026/08/kukis_8551.jpg",
        author = "Дилноза Каримова",
        cuisine = "Американская",
        difficulty = "Легко",
        rating = 4.9f,
        reviews = 3200,
        prepMinutes = 20,
        cookMinutes = 12,
        servings = 12,
        calories = 290,
        summary = "Хрустящие по краям и тягучие в середине печенья с крупными каплями шоколада. " +
            "Тесто можно замесить заранее и держать в морозилке.",
        tags = listOf("К чаю", "Выпечка", "Шоколад"),
        ingredients = listOf(
            Ingredient("Мука", 250.0, "г"),
            Ingredient("Сливочное масло", 150.0, "г"),
            Ingredient("Коричневый сахар", 120.0, "г"),
            Ingredient("Сахар", 80.0, "г"),
            Ingredient("Яйцо", 1.0, "шт"),
            Ingredient("Тёмный шоколад", 200.0, "г"),
            Ingredient("Разрыхлитель", 1.0, "ч. л."),
            Ingredient("Соль", 0.5, "ч. л.")
        ),
        steps = listOf(
            CookStep("Растопите масло до орехового аромата и остудите.", 8),
            CookStep("Взбейте масло с двумя видами сахара и яйцом до гладкости.", 4),
            CookStep("Вмешайте муку с разрыхлителем и солью, добавьте рубленый шоколад.", 5),
            CookStep("Охладите тесто в холодильнике, чтобы печенье не растекалось.", 30),
            CookStep("Выпекайте шарики теста при 180 °C до подрумяненных краёв.", 12),
            CookStep("Дайте печенью 10 минут постоять на противне — середина дойдёт сама.", 10)
        ),
        tip = "Достаньте кукис, когда центр ещё кажется сырым: на горячем противне он схватится до идеала."
    ),
    Recipe(
        id = 12,
        title = "Мороженое из арбуза и дыни",
        categoryKey = "dessert",
        imageUrl = "${ZiraImages.BASE}/2026/07/9i3a0795-2-2.jpg",
        author = "Артур Ким",
        cuisine = "Домашняя",
        difficulty = "Легко",
        rating = 4.4f,
        reviews = 610,
        prepMinutes = 15,
        cookMinutes = 0,
        servings = 6,
        calories = 140,
        summary = "Трендовый десерт прямо в замороженных половинках арбуза и дыни. " +
            "Три ингредиента, никакой мороженицы.",
        tags = listOf("Лето", "Без выпечки", "Фрукты"),
        ingredients = listOf(
            Ingredient("Арбуз", 0.5, "шт"),
            Ingredient("Дыня", 0.5, "шт"),
            Ingredient("Сливки 33%", 200.0, "мл"),
            Ingredient("Сгущённое молоко", 150.0, "г"),
            Ingredient("Лайм", 1.0, "шт")
        ),
        steps = listOf(
            CookStep("Выберите мякоть, оставив плотные стенки — это будут чаши.", 10),
            CookStep("Заморозьте мякоть кусочками до твёрдости.", 240),
            CookStep("Пробейте фрукты блендером со сгущённым молоком.", 3),
            CookStep("Взбейте сливки и аккуратно вмешайте в фруктовое пюре.", 4),
            CookStep("Верните массу в чаши и отправьте в морозилку до подачи.", 120)
        )
    )
)
