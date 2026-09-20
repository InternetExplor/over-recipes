package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.ui.components.RatingRow
import com.wtduyuwnt.overrecipes.ui.components.RecipeImage
import com.wtduyuwnt.overrecipes.ui.components.TagChip
import com.wtduyuwnt.overrecipes.ui.components.imageScrim
import com.wtduyuwnt.overrecipes.util.formatReviews

val RecipeHeaderHeight = 380.dp

@Composable
fun RecipeDetailHeader(
    recipe: Recipe,
    parallax: () -> Float,
    modifier: Modifier = Modifier,
    onPlayVideo: (() -> Unit)? = null
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(RecipeHeaderHeight)
            .clipToBounds()
    ) {
        RecipeImage(
            url = recipe.imageUrl,
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationY = parallax() }
        )
        Box(Modifier.fillMaxSize().background(imageScrim()))
        if (onPlayVideo != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = onPlayVideo),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Смотреть видеорецепт",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Column(
            Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
        ) {
            val chips = listOf(recipe.cuisine, recipe.difficulty).filter { it.isNotBlank() }
            if (chips.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    chips.forEach { chip ->
                        TagChip(
                            text = chip,
                            container = Color.White.copy(alpha = 0.16f),
                            content = Color.White
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
            }
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.displaySmall,
                color = Color.White
            )
            Spacer(Modifier.height(10.dp))
            RatingRow(
                rating = recipe.rating,
                trailing = recipe.reviews.takeIf { it > 0 }?.let(::formatReviews),
                contentColor = Color.White
            )
        }
    }
}
