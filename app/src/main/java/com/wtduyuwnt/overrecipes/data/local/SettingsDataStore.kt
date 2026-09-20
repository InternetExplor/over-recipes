package com.wtduyuwnt.overrecipes.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wtduyuwnt.overrecipes.data.model.AppLanguage
import com.wtduyuwnt.overrecipes.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.settingsStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    private val languageKey = stringPreferencesKey("recipe_language")
    private val themeKey = stringPreferencesKey("theme_mode")

    private val preferences: Flow<Preferences> = context.settingsStore.data
        .catch { error -> if (error is IOException) emit(emptyPreferences()) else throw error }

    val language: Flow<AppLanguage> = preferences.map { AppLanguage.fromCode(it[languageKey]) }

    val themeMode: Flow<ThemeMode> = preferences.map { ThemeMode.fromId(it[themeKey]) }

    suspend fun setLanguage(language: AppLanguage) {
        context.settingsStore.edit { it[languageKey] = language.code }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.settingsStore.edit { it[themeKey] = mode.id }
    }
}
