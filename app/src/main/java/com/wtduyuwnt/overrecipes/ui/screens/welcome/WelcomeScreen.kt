package com.wtduyuwnt.overrecipes.ui.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.data.mock.MockRecipes
import com.wtduyuwnt.overrecipes.ui.components.RecipeImage

@Composable
fun WelcomeScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        RecipeImage(
            url = MockRecipes.all.first().imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Black.copy(alpha = 0.35f),
                        0.4f to Color.Black.copy(alpha = 0.55f),
                        1f to Color.Black.copy(alpha = 0.92f)
                    )
                )
        )

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(700)) + slideInVertically(tween(700)) { it / 5 },
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Text(
                    text = "OSHXONA · ДОМАШНЯЯ КУХНЯ",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Готовьте вкусно каждый день",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White
                )
                Spacer(Modifier.height(14.dp))
                Text(
                    text = "Пошаговые рецепты, точные пропорции и подборки на каждый повод — " +
                        "от чайханского плова до десертов за 15 минут.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.78f)
                )
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = onRegister,
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Text("Создать аккаунт", style = MaterialTheme.typography.labelLarge)
                }
                Spacer(Modifier.height(4.dp))
                TextButton(
                    onClick = onLogin,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White.copy(alpha = 0.8f))
                ) {
                    Text("Уже есть аккаунт — войти")
                }
            }
        }
    }
}
