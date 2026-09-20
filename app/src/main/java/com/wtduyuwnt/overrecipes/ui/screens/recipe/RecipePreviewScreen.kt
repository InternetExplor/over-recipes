package com.wtduyuwnt.overrecipes.ui.screens.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.components.EmptyState
import com.wtduyuwnt.overrecipes.ui.components.ErrorState
import com.wtduyuwnt.overrecipes.ui.components.FavoriteButton
import com.wtduyuwnt.overrecipes.ui.components.RecipeImage
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.RecipePreviewSheet
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.RecipePreviewSkeleton
import com.wtduyuwnt.overrecipes.ui.state.RecipeUiState

@Composable
fun RecipePreviewScreen(
    state: RecipeUiState,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onOpenFull: () -> Unit
) {
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val sheetMaxHeight = maxHeight * 0.78f
        val recipe = state.recipe

        if (recipe != null) {
            RecipeImage(
                url = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Black.copy(alpha = 0.45f),
                            0.35f to Color.Transparent
                        )
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val onPhoto = recipe != null
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        if (onPhoto) Color.Black.copy(alpha = 0.35f)
                        else MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = if (onPhoto) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            if (recipe != null) {
                FavoriteButton(isFavorite = state.isFavorite, onToggle = onToggleFavorite)
            }
        }

        if (recipe != null && recipe.hasVideo && !state.isLoading) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 140.dp)
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = onOpenFull),
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

        when {
            state.isLoading -> RecipePreviewSkeleton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )

            state.hasError -> ErrorState(
                message = state.errorMessage.orEmpty(),
                modifier = Modifier.align(Alignment.Center),
                onRetry = onRetry
            )

            recipe == null -> EmptyState(
                title = "Рецепт не найден",
                description = "Возможно, он был удалён с сервера.",
                modifier = Modifier.align(Alignment.Center),
                action = { TextButton(onClick = onBack) { Text("Назад") } }
            )

            else -> RecipePreviewSheet(
                recipe = recipe,
                maxHeight = sheetMaxHeight,
                onOpenFull = onOpenFull,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
