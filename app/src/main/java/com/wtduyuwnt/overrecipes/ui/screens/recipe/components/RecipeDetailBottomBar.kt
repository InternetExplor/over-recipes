package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.components.FavoriteButton

@Composable
fun RecipeDetailBottomBar(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onStartCooking: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FavoriteButton(
                isFavorite = isFavorite,
                onToggle = onToggleFavorite,
                container = MaterialTheme.colorScheme.surfaceContainerHighest,
                content = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.width(12.dp))
            Button(
                onClick = onStartCooking,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
            ) {
                Text("Начать готовку", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
