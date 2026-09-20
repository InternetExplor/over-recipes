package com.wtduyuwnt.overrecipes.data.model

enum class ThemeMode(val id: String, val label: String) {
    SYSTEM("system", "Как в системе"),
    LIGHT("light", "Светлая"),
    DARK("dark", "Тёмная");

    companion object {
        fun fromId(id: String?): ThemeMode =
            entries.firstOrNull { it.id == id } ?: SYSTEM
    }
}
