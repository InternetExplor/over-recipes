package com.wtduyuwnt.overrecipes.di

import android.content.Context
import com.wtduyuwnt.overrecipes.data.local.AccountDataStore
import com.wtduyuwnt.overrecipes.data.local.FavoritesDataStore
import com.wtduyuwnt.overrecipes.data.local.SettingsDataStore
import com.wtduyuwnt.overrecipes.data.remote.NetworkModule
import com.wtduyuwnt.overrecipes.data.repository.AccountRepository
import com.wtduyuwnt.overrecipes.data.repository.ApiRecipeRepository
import com.wtduyuwnt.overrecipes.data.repository.FavoritesRepository
import com.wtduyuwnt.overrecipes.data.repository.RecipeRepository
import com.wtduyuwnt.overrecipes.data.repository.SettingsRepository

object AppGraph {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    val accountRepository: AccountRepository by lazy {
        AccountRepository(AccountDataStore(applicationContext))
    }

    val settingsRepository: SettingsRepository by lazy {
        SettingsRepository(SettingsDataStore(applicationContext))
    }

    val recipeRepository: RecipeRepository by lazy {
        ApiRecipeRepository(NetworkModule.createApi(), settingsRepository)
    }

    val favoritesRepository: FavoritesRepository by lazy {
        FavoritesRepository(FavoritesDataStore(applicationContext))
    }
}
