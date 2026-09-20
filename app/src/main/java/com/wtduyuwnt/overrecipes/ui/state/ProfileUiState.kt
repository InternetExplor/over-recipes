package com.wtduyuwnt.overrecipes.ui.state

import com.wtduyuwnt.overrecipes.data.model.AppLanguage
import com.wtduyuwnt.overrecipes.data.model.ThemeMode

data class ProfileUiState(
    val name: String = "Гость",
    val email: String = "",
    val favoritesCount: Int = 0,
    val categoriesCount: Int = 0,
    val language: AppLanguage = AppLanguage.RUSSIAN,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val appVersion: String = "1.0"
) {
    val subtitle: String
        get() = email.ifBlank { "Домашний повар" }
}
