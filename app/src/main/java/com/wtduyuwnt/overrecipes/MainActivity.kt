package com.wtduyuwnt.overrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wtduyuwnt.overrecipes.ui.OverRecipesApp
import com.wtduyuwnt.overrecipes.ui.theme.OverRecipesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OverRecipesTheme {
                OverRecipesApp()
            }
        }
    }
}
