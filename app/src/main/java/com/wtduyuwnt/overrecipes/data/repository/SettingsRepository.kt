package com.wtduyuwnt.overrecipes.data.repository

import com.wtduyuwnt.overrecipes.data.local.SettingsDataStore
import com.wtduyuwnt.overrecipes.data.model.AppLanguage
import com.wtduyuwnt.overrecipes.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow

class SettingsRepository(
    private val dataStore: SettingsDataStore
) {
    val language: Flow<AppLanguage> = dataStore.language

    val themeMode: Flow<ThemeMode> = dataStore.themeMode

    suspend fun setLanguage(language: AppLanguage) = dataStore.setLanguage(language)

    suspend fun setThemeMode(mode: ThemeMode) = dataStore.setThemeMode(mode)
}
