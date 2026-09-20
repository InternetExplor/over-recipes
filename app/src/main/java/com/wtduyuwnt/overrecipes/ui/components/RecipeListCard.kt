package com.wtduyuwnt.overrecipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.util.formatMinutes
import com.wtduyuwnt.overrecipes.util.joinMeta

@Composable
fun RecipeListCard(
    recipe: Recipe,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RecipeImage(
                url = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .size(96.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 14.dp)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                val meta = joinMeta(
                    recipe.totalMinutes.takeIf { it > 0 }?.let(::formatMinutes),
                    recipe.difficulty.takeIf { it.isNotBlank() },
                    recipe.calories.takeIf { it > 0 }?.let { "$it ккал" }
                )
                if (meta.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = meta,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(8.dp))
                RatingRow(rating = recipe.rating, starSize = 13.dp)
            }
            FavoriteButton(
                isFavorite = isFavorite,
                onToggle = onToggleFavorite,
                container = MaterialTheme.colorScheme.surfaceContainerHigh,
                content = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
