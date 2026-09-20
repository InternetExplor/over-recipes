package com.wtduyuwnt.overrecipes.data.model

enum class AppLanguage(val code: String, val label: String) {
    RUSSIAN("ru", "Русский"),
    UZBEK("uz", "O\'zbekcha");

    companion object {
        fun fromCode(code: String?): AppLanguage =
            entries.firstOrNull { it.code == code } ?: RUSSIAN
    }
}
