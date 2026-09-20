package com.wtduyuwnt.overrecipes.data.mock

import com.wtduyuwnt.overrecipes.data.model.CookStep
import com.wtduyuwnt.overrecipes.data.model.Ingredient
import com.wtduyuwnt.overrecipes.data.model.Recipe

internal val mainDishRecipes: List<Recipe> = listOf(
    Recipe(
        id = 1,
        title = "Чайханский плов",
        categoryKey = "main-dishes",
        imageUrl = "${ZiraImages.BASE}/2018/10/chaykhanskiy-plov-5.jpg",
        author = "Ситора Юсупова",
        cuisine = "Узбекская",
        difficulty = "Средне",
        rating = 4.9f,
        reviews = 4500,
        prepMinutes = 30,
        cookMinutes = 90,
        servings = 6,
        calories = 610,
        summary = "Тот самый плов из чайханы: баранина, курдючный жир и рис, томлённый над зирваком. " +
            "Готовится в казане, подаётся с перепелиными яйцами и казы.",
        tags = listOf("Казан", "Праздничное", "Мясо"),
        ingredients = listOf(
            Ingredient("Баранина", 1.0, "кг"),
            Ingredient("Курдючный жир", 300.0, "г"),
            Ingredient("Морковь", 1.2, "кг"),
            Ingredient("Рис девзира", 1.0, "кг"),
            Ingredient("Лук", 300.0, "г"),
            Ingredient("Чеснок", 2.0, "головки"),
            Ingredient("Зира", 15.0, "г"),
            Ingredient("Соль", 50.0, "г")
        ),
        steps = listOf(
            CookStep("Нарежьте курдючный жир кубиком и вытопите в раскалённом казане. Шкварки выньте шумовкой.", 15),
            CookStep("Обжарьте лук, нарезанный полукольцами, до золотистого цвета.", 7),
            CookStep("Добавьте мясо крупными кусками и обжарьте до румяной корочки.", 10),
            CookStep("Всыпьте морковь соломкой, жарьте до полуготовности, не перемешивая первые минуты.", 10),
            CookStep("Влейте 800 мл кипятка, положите чеснок и стручок перца, томите зирвак под крышкой.", 40),
            CookStep("Промойте рис и замочите в тёплой подсоленной воде, пока готовится зирвак.", 20),
            CookStep("Выложите рис ровным слоем, посолите, посыпьте зирой и дайте воде выпариться.", 20),
            CookStep("Соберите рис горкой, закройте крышкой и дайте плову дойти на слабом огне.", 20)
        ),
        videoUrl = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        tip = "Для плова берите рис с низким содержанием крахмала — девзира, ошпар или лазер. Тогда зёрна останутся рассыпчатыми."
    ),
    Recipe(
        id = 2,
        title = "Паста путтанеска",
        categoryKey = "main-dishes",
        imageUrl = "${ZiraImages.BASE}/2026/08/recept_po_filmu_pasta_putanesko_0858.jpg",
        author = "Никита Бычков",
        cuisine = "Итальянская",
        difficulty = "Легко",
        rating = 4.6f,
        reviews = 1240,
        prepMinutes = 10,
        cookMinutes = 20,
        servings = 2,
        calories = 480,
        summary = "Дерзкий неаполитанский соус из томатов, каперсов, оливок и анчоусов. " +
            "Готовится ровно столько, сколько варится паста.",
        tags = listOf("Быстро", "Ужин", "Паста"),
        ingredients = listOf(
            Ingredient("Спагетти", 200.0, "г"),
            Ingredient("Томаты в собственном соку", 400.0, "г"),
            Ingredient("Оливки таджаска", 60.0, "г"),
            Ingredient("Каперсы", 20.0, "г"),
            Ingredient("Анчоусы", 4.0, "филе"),
            Ingredient("Чеснок", 3.0, "зубчика"),
            Ingredient("Оливковое масло", 3.0, "ст. л."),
            Ingredient("Хлопья чили", 1.0, "щепотка")
        ),
        steps = listOf(
            CookStep("Поставьте кастрюлю с подсоленной водой на сильный огонь.", 8),
            CookStep("Прогрейте оливковое масло с чесноком и чили, растворите в нём анчоусы.", 3),
            CookStep("Добавьте оливки и каперсы, прогрейте до появления аромата.", 2),
            CookStep("Влейте томаты, разомните лопаткой и уваривайте соус.", 10),
            CookStep("Отварите спагетти на минуту меньше, чем указано на упаковке.", 8),
            CookStep("Переложите пасту в соус, добавьте половник воды от варки и эмульгируйте.", 2)
        ),
        tip = "Соль в соус почти не нужна: её достаточно в анчоусах, каперсах и оливках."
    ),
    Recipe(
        id = 3,
        title = "Бургер «Неряха Джо»",
        categoryKey = "main-dishes",
        imageUrl = "${ZiraImages.BASE}/2026/07/recept_po_filmu_burger_neryakha_dzho_0006.jpg",
        author = "Дилноза Каримова",
        cuisine = "Американская",
        difficulty = "Легко",
        rating = 4.7f,
        reviews = 860,
        prepMinutes = 15,
        cookMinutes = 25,
        servings = 4,
        calories = 720,
        summary = "Сочный фарш в густом томатном соусе, который невозможно съесть аккуратно. " +
            "Мягкая булочка, много начинки и никаких шансов остаться чистым.",
        tags = listOf("Стрит-фуд", "Говядина", "Для компании"),
        ingredients = listOf(
            Ingredient("Говяжий фарш", 600.0, "г"),
            Ingredient("Булочки бриошь", 4.0, "шт"),
            Ingredient("Лук", 1.0, "шт"),
            Ingredient("Томатная паста", 2.0, "ст. л."),
            Ingredient("Кетчуп", 100.0, "г"),
            Ingredient("Вустерский соус", 1.0, "ст. л."),
            Ingredient("Коричневый сахар", 1.0, "ч. л."),
            Ingredient("Копчёная паприка", 1.0, "ч. л.")
        ),
        steps = listOf(
            CookStep("Обжарьте мелко нарезанный лук до мягкости.", 5),
            CookStep("Добавьте фарш и жарьте, разбивая комочки, до румяности.", 8),
            CookStep("Вмешайте томатную пасту и паприку, прогрейте минуту.", 2),
            CookStep("Влейте кетчуп, вустерский соус, сахар и немного воды. Тушите до густоты.", 10),
            CookStep("Подрумяньте разрезанные булочки на сухой сковороде.", 2),
            CookStep("Выложите начинку горкой и сразу подавайте с солёными огурцами.")
        ),
        tip = "Соус должен держаться на ложке — иначе булочка размокнет за минуту."
    ),
    Recipe(
        id = 4,
        title = "Охотничьи колбаски на гриле",
        categoryKey = "main-dishes",
        imageUrl = "${ZiraImages.BASE}/2026/07/sherin_okhotnichi_kolbaski_9819.jpg",
        author = "Артур Ким",
        cuisine = "Европейская",
        difficulty = "Легко",
        rating = 4.4f,
        reviews = 320,
        prepMinutes = 10,
        cookMinutes = 15,
        servings = 4,
        calories = 540,
        summary = "Подкопчённые колбаски с хрустящей корочкой, карамелизованным луком и горчичным соусом. " +
            "Идеально для вечера с друзьями.",
        tags = listOf("Гриль", "Закуска", "Быстро"),
        ingredients = listOf(
            Ingredient("Охотничьи колбаски", 8.0, "шт"),
            Ingredient("Лук красный", 2.0, "шт"),
            Ingredient("Дижонская горчица", 2.0, "ст. л."),
            Ingredient("Мёд", 1.0, "ст. л."),
            Ingredient("Яблочный уксус", 1.0, "ч. л."),
            Ingredient("Чиабатта", 4.0, "куска"),
            Ingredient("Розмарин", 2.0, "веточки")
        ),
        steps = listOf(
            CookStep("Надрежьте колбаски по диагонали, чтобы они не лопнули.", 3),
            CookStep("Разогрейте гриль-сковороду до лёгкого дымка.", 4),
            CookStep("Обжарьте колбаски с двух сторон вместе с розмарином.", 8),
            CookStep("Карамелизуйте лук с мёдом и уксусом на соседней сковороде.", 7),
            CookStep("Смешайте горчицу с остатками мёда и подавайте как соус.")
        )
    )
)
