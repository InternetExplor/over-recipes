package com.wtduyuwnt.overrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wtduyuwnt.overrecipes.data.model.ThemeMode
import com.wtduyuwnt.overrecipes.di.AppGraph
import com.wtduyuwnt.overrecipes.ui.OverRecipesApp
import com.wtduyuwnt.overrecipes.ui.theme.OverRecipesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by AppGraph.settingsRepository.themeMode
                .collectAsStateWithLifecycle(initialValue = ThemeMode.SYSTEM)

            val darkTheme = when (themeMode) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            OverRecipesTheme(darkTheme = darkTheme) {
                OverRecipesApp()
            }
        }
    }
}
