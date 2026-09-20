package com.wtduyuwnt.overrecipes.data.repository

import com.wtduyuwnt.overrecipes.data.local.FavoritesDataStore
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(
    private val dataStore: FavoritesDataStore
) {
    val favorites: Flow<Set<Int>> = dataStore.favorites

    suspend fun toggle(recipeId: Int) {
        dataStore.toggle(recipeId)
    }
}
