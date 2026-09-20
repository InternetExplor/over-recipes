package com.wtduyuwnt.overrecipes.data.remote.mapper

import com.wtduyuwnt.overrecipes.data.model.AppLanguage

object CategoryTitles {

    private val russian = mapOf(
        "nonushta" to "Завтраки",
        "sho-rva" to "Супы",
        "sho'rva" to "Супы",
        "shorva" to "Супы",
        "go-sht" to "Мясные блюда",
        "go'sht" to "Мясные блюда",
        "gosht" to "Мясные блюда",
        "baliq" to "Рыбные блюда",
        "salat" to "Салаты",
        "salatlar" to "Салаты",
        "shirinlik" to "Десерты",
        "shirinliklar" to "Десерты",
        "desert" to "Десерты",
        "ichimlik" to "Напитки",
        "ichimliklar" to "Напитки",
        "xamir" to "Выпечка",
        "pishiriq" to "Выпечка",
        "non" to "Хлеб",
        "garnir" to "Гарниры",
        "sous" to "Соусы",
        "gazak" to "Закуски",
        "sabzavot" to "Овощные блюда",
        "quyuq-taomlar" to "Вторые блюда",
        "tez" to "Быстрые рецепты",
        "milliy" to "Национальная кухня"
    )

    fun localize(key: String, remoteTitle: String, language: AppLanguage): String {
        if (remoteTitle.isNotBlank() && !remoteTitle.equals(key, ignoreCase = true)) return remoteTitle
        if (language == AppLanguage.RUSSIAN) {
            russian[key.lowercase()]?.let { return it }
        }
        return key.replace('-', ' ').replaceFirstChar { it.uppercase() }
    }
}
