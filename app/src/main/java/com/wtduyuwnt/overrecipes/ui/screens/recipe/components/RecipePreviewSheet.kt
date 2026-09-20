package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.ui.components.MetaColumn
import com.wtduyuwnt.overrecipes.ui.components.RatingRow
import com.wtduyuwnt.overrecipes.util.formatMinutesOrDash
import com.wtduyuwnt.overrecipes.util.formatReviews

@Composable
fun RecipePreviewSheet(
    recipe: Recipe,
    maxHeight: Dp,
    onOpenFull: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = maxHeight),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        Column(
            Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 24.dp)
        ) {
            Box(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(36.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
            Spacer(Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "АВТОР",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = recipe.author.ifBlank { "Zira.uz" },
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Поделиться",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(12.dp))
            RatingRow(
                rating = recipe.rating,
                trailing = recipe.reviews.takeIf { it > 0 }?.let(::formatReviews),
                starSize = 17.dp
            )
            Spacer(Modifier.height(16.dp))
            if (recipe.summary.isNotBlank()) {
                Text(
                    text = recipe.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.height(20.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MetaColumn(
                        label = "Время",
                        value = formatMinutesOrDash(recipe.totalMinutes),
                        alignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        modifier = Modifier.height(32.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                    MetaColumn(
                        label = "Порций",
                        value = "${recipe.servings}",
                        alignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        modifier = Modifier.height(32.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                    MetaColumn(
                        label = "Сложность",
                        value = recipe.difficulty.ifBlank { "—" },
                        alignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = onOpenFull,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Смотреть рецепт", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
