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

@Composable
fun WideRecipeCard(
    recipe: Recipe,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 10f)
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
            if (recipe.author.isNotBlank()) {
                Text(
                    text = recipe.author.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(Modifier.height(6.dp))
            }
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
            RatingRow(rating = recipe.rating, contentColor = Color.White)
        }
    }
}
