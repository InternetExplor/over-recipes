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
fun FeaturedRecipeCard(
    recipe: Recipe,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(268.dp)
            .height(340.dp)
            .clip(MaterialTheme.shapes.large)
            .clickable(onClick = onClick)
    ) {
        RecipeImage(recipe.imageUrl, recipe.title, Modifier.fillMaxSize())
        Box(Modifier.fillMaxSize().background(imageScrim()))
        FavoriteButton(
            isFavorite = isFavorite,
            onToggle = onToggleFavorite,
            modifier = Modifier.align(Alignment.TopEnd).padding(10.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(18.dp)
        ) {
            if (recipe.cuisine.isNotBlank()) {
                TagChip(
                    text = recipe.cuisine,
                    container = Color.White.copy(alpha = 0.16f),
                    content = Color.White
                )
                Spacer(Modifier.height(10.dp))
            }
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            val meta = joinMeta(
                recipe.author.takeIf { it.isNotBlank() },
                recipe.totalMinutes.takeIf { it > 0 }?.let(::formatMinutes)
            )
            if (meta.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(
                    text = meta,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.75f)
                )
            }
            Spacer(Modifier.height(10.dp))
            RatingRow(rating = recipe.rating, contentColor = Color.White)
        }
    }
}
