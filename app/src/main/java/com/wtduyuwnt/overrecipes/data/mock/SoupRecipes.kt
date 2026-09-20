package com.wtduyuwnt.overrecipes.data.mock

import com.wtduyuwnt.overrecipes.data.model.CookStep
import com.wtduyuwnt.overrecipes.data.model.Ingredient
import com.wtduyuwnt.overrecipes.data.model.Recipe

internal val soupRecipes: List<Recipe> = listOf(
    Recipe(
        id = 5,
        title = "Рамен с мясом",
        categoryKey = "soups",
        imageUrl = "${ZiraImages.BASE}/2016/02/ramen-s-myasom-hot-lunch-11-2.jpg",
        author = "Хот Ланч",
        cuisine = "Японская",
        difficulty = "Средне",
        rating = 4.8f,
        reviews = 2100,
        prepMinutes = 20,
        cookMinutes = 40,
        servings = 2,
        calories = 590,
        summary = "Насыщенный бульон, тягучая лапша, маринованное яйцо и тонкие ломтики свинины. " +
            "Домашний рамен, который не уступает лапшичной.",
        tags = listOf("Азия", "Сытно", "Бульон"),
        ingredients = listOf(
            Ingredient("Лапша рамен", 200.0, "г"),
            Ingredient("Свиная шея", 400.0, "г"),
            Ingredient("Куриный бульон", 1.0, "л"),
            Ingredient("Соевый соус", 3.0, "ст. л."),
            Ingredient("Мирин", 2.0, "ст. л."),
            Ingredient("Яйца", 2.0, "шт"),
            Ingredient("Имбирь", 20.0, "г"),
            Ingredient("Зелёный лук", 2.0, "стебля")
        ),
        steps = listOf(
            CookStep("Обжарьте свинину до корочки, затем томите в бульоне с имбирём.", 30),
            CookStep("Сварите яйца всмятку и замаринуйте в соевом соусе с мирином.", 10),
            CookStep("Процедите бульон, доведите до вкуса соевым соусом.", 5),
            CookStep("Отварите лапшу отдельно и сразу промойте.", 4),
            CookStep("Соберите пиалу: лапша, бульон, ломтики мяса, половинки яйца, зелёный лук.")
        ),
        tip = "Лапшу никогда не варите в бульоне — крахмал сделает его мутным."
    ),
    Recipe(
        id = 6,
        title = "Суп том ям",
        categoryKey = "soups",
        imageUrl = "${ZiraImages.BASE}/2025/12/cx7a9730.jpg",
        author = "Никита Бычков",
        cuisine = "Тайская",
        difficulty = "Средне",
        rating = 4.7f,
        reviews = 1580,
        prepMinutes = 15,
        cookMinutes = 25,
        servings = 4,
        calories = 320,
        summary = "Острый и кислый тайский суп с креветками, лемонграссом и кокосовым молоком. " +
            "Самый согревающий вариант для холодных дней.",
        tags = listOf("Остро", "Морепродукты", "Азия"),
        ingredients = listOf(
            Ingredient("Креветки", 400.0, "г"),
            Ingredient("Куриный бульон", 1.0, "л"),
            Ingredient("Кокосовое молоко", 200.0, "мл"),
            Ingredient("Паста том ям", 2.0, "ст. л."),
            Ingredient("Лемонграсс", 2.0, "стебля"),
            Ingredient("Шампиньоны", 200.0, "г"),
            Ingredient("Лайм", 1.0, "шт"),
            Ingredient("Листья каффир-лайма", 4.0, "шт")
        ),
        steps = listOf(
            CookStep("Раздавите лемонграсс и проварите в бульоне с листьями лайма.", 10),
            CookStep("Добавьте пасту том ям и грибы, дайте закипеть.", 5),
            CookStep("Влейте кокосовое молоко и прогрейте, не доводя до бурного кипения.", 5),
            CookStep("Опустите креветки и варите до изменения цвета.", 3),
            CookStep("Снимите с огня, добавьте сок лайма и кинзу.")
        ),
        tip = "Сок лайма добавляйте только в снятый с огня суп — при кипении он горчит."
    ),
    Recipe(
        id = 7,
        title = "Чечевичный суп",
        categoryKey = "soups",
        imageUrl = "${ZiraImages.BASE}/2015/01/chechevichnyy-sup-2.jpg",
        author = "Ситора Юсупова",
        cuisine = "Ближневосточная",
        difficulty = "Легко",
        rating = 4.5f,
        reviews = 940,
        prepMinutes = 10,
        cookMinutes = 35,
        servings = 4,
        calories = 260,
        summary = "Густой суп-пюре из красной чечевицы с копчёной паприкой и лимоном. " +
            "Простой, сытный и готовится из того, что есть в шкафу.",
        tags = listOf("Вегетарианское", "Бюджетно", "Обед"),
        ingredients = listOf(
            Ingredient("Красная чечевица", 250.0, "г"),
            Ingredient("Морковь", 1.0, "шт"),
            Ingredient("Лук", 1.0, "шт"),
            Ingredient("Чеснок", 2.0, "зубчика"),
            Ingredient("Томатная паста", 1.0, "ст. л."),
            Ingredient("Зира", 1.0, "ч. л."),
            Ingredient("Копчёная паприка", 1.0, "ч. л."),
            Ingredient("Лимон", 0.5, "шт")
        ),
        steps = listOf(
            CookStep("Обжарьте лук и морковь до мягкости.", 7),
            CookStep("Добавьте чеснок, зиру и паприку, прогрейте до аромата.", 2),
            CookStep("Вмешайте томатную пасту, всыпьте промытую чечевицу.", 2),
            CookStep("Влейте 1,2 л воды и варите до разваривания чечевицы.", 25),
            CookStep("Пробейте блендером до кремовой текстуры и заправьте лимоном.", 3)
        )
    ),
    Recipe(
        id = 8,
        title = "Солянка сборная",
        categoryKey = "soups",
        imageUrl = "${ZiraImages.BASE}/2024/06/sbornaya-solyanka-sherin.jpg",
        author = "Дилноза Каримова",
        cuisine = "Русская",
        difficulty = "Средне",
        rating = 4.6f,
        reviews = 1320,
        prepMinutes = 20,
        cookMinutes = 60,
        servings = 6,
        calories = 410,
        summary = "Наваристый суп с копчёностями, солёными огурцами, каперсами и оливками. " +
            "Подаётся со сметаной и долькой лимона.",
        tags = listOf("Сытно", "Мясо", "Классика"),
        ingredients = listOf(
            Ingredient("Говядина на кости", 500.0, "г"),
            Ingredient("Копчёные колбаски", 200.0, "г"),
            Ingredient("Ветчина", 150.0, "г"),
            Ingredient("Солёные огурцы", 3.0, "шт"),
            Ingredient("Оливки", 100.0, "г"),
            Ingredient("Томатная паста", 2.0, "ст. л."),
            Ingredient("Лук", 2.0, "шт"),
            Ingredient("Лимон", 1.0, "шт")
        ),
        steps = listOf(
            CookStep("Сварите крепкий мясной бульон, снимая пену.", 60),
            CookStep("Обжарьте лук с томатной пастой до тёмного цвета.", 8),
            CookStep("Добавьте нарезанные соломкой копчёности и обжарьте.", 7),
            CookStep("Припустите огурцы отдельно, чтобы они не задубели.", 5),
            CookStep("Соедините всё в бульоне, добавьте оливки и каперсы, проварите.", 10),
            CookStep("Дайте настояться под крышкой и подавайте с лимоном и сметаной.", 15)
        ),
        tip = "Солянка вкуснее на следующий день — дайте ей полностью остыть и прогрейте заново."
    ),
    Recipe(
        id = 9,
        title = "Пряный суп с курицей",
        categoryKey = "soups",
        imageUrl = "${ZiraImages.BASE}/2024/02/pryanyy-sup-s-kuricey.jpg",
        author = "Артур Ким",
        cuisine = "Домашняя",
        difficulty = "Легко",
        rating = 4.3f,
        reviews = 480,
        prepMinutes = 15,
        cookMinutes = 45,
        servings = 4,
        calories = 350,
        summary = "Согревающий куриный суп с корицей и кориандром. Секрет аромата — специи, " +
            "обжаренные в масле перед закладкой.",
        tags = listOf("Комфорт-фуд", "Курица", "Зима"),
        ingredients = listOf(
            Ingredient("Куриные бёдра", 600.0, "г"),
            Ingredient("Картофель", 4.0, "шт"),
            Ingredient("Морковь", 1.0, "шт"),
            Ingredient("Лук", 1.0, "шт"),
            Ingredient("Корица", 1.0, "палочка"),
            Ingredient("Кориандр", 1.0, "ч. л."),
            Ingredient("Сливочное масло", 30.0, "г")
        ),
        steps = listOf(
            CookStep("Обжарьте бёдра на сливочном масле до золотистой корочки.", 10),
            CookStep("Добавьте лук и морковь, прогрейте со специями.", 7),
            CookStep("Влейте 1,5 л воды, положите корицу и варите на слабом огне.", 25),
            CookStep("Добавьте картофель кубиком и доведите до готовности.", 15),
            CookStep("Выньте корицу, разберите мясо и верните в суп.")
        )
    ),
    Recipe(
        id = 10,
        title = "Окрошка в ледяной чаше",
        categoryKey = "soups",
        imageUrl = "${ZiraImages.BASE}/2026/07/sherin-okroshka.jpg",
        author = "Ситора Юсупова",
        cuisine = "Домашняя",
        difficulty = "Легко",
        rating = 4.5f,
        reviews = 760,
        prepMinutes = 25,
        cookMinutes = 0,
        servings = 4,
        calories = 210,
        summary = "Освежающая окрошка на кефире с эффектной подачей в чаше изо льда. " +
            "Готовится без плиты, держит холод до конца обеда.",
        tags = listOf("Лето", "Холодный суп", "Без плиты"),
        ingredients = listOf(
            Ingredient("Кефир", 1.0, "л"),
            Ingredient("Отварной картофель", 3.0, "шт"),
            Ingredient("Яйца", 4.0, "шт"),
            Ingredient("Огурцы", 3.0, "шт"),
            Ingredient("Варёная колбаса", 250.0, "г"),
            Ingredient("Редис", 6.0, "шт"),
            Ingredient("Укроп", 1.0, "пучок"),
            Ingredient("Горчица", 1.0, "ч. л.")
        ),
        steps = listOf(
            CookStep("Заморозьте воду с зеленью и ломтиками лимона в двух мисках — получится чаша.", 240),
            CookStep("Нарежьте картофель, яйца, огурцы, редис и колбасу мелким кубиком.", 15),
            CookStep("Разотрите часть яичного желтка с горчицей и солью.", 3),
            CookStep("Соедините нарезку с кефиром и заправкой, охладите.", 20),
            CookStep("Достаньте ледяную чашу, перелейте окрошку и подавайте сразу.")
        ),
        tip = "Чашу морозьте с вечера: за 4 часа лёд получается хрупким и трескается при подаче."
    )
)
