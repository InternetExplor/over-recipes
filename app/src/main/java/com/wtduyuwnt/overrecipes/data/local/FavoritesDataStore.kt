package com.wtduyuwnt.overrecipes.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.favoritesStore: DataStore<Preferences> by preferencesDataStore(name = "favorites")

class FavoritesDataStore(private val context: Context) {

    private val key = stringSetPreferencesKey("favorite_recipe_ids")

    val favorites: Flow<Set<Int>> = context.favoritesStore.data
        .catch { error -> if (error is IOException) emit(emptyPreferences()) else throw error }
        .map { preferences ->
            preferences[key].orEmpty().mapNotNull(String::toIntOrNull).toSet()
        }

    suspend fun toggle(recipeId: Int) {
        context.favoritesStore.edit { preferences ->
            val current = preferences[key].orEmpty()
            val id = recipeId.toString()
            preferences[key] = if (current.contains(id)) current - id else current + id
        }
    }
}
