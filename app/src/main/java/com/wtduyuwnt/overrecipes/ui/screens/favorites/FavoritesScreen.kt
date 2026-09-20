package com.wtduyuwnt.overrecipes.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.components.EmptyState
import com.wtduyuwnt.overrecipes.ui.components.ErrorState
import com.wtduyuwnt.overrecipes.ui.components.SkeletonBox
import com.wtduyuwnt.overrecipes.ui.components.WideRecipeCard
import com.wtduyuwnt.overrecipes.ui.state.FavoritesUiState

@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    onToggleFavorite: (Int) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onBrowse: () -> Unit,
    contentPadding: PaddingValues
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "ИЗБРАННОЕ",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Сохранённые рецепты",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 8.dp,
                bottom = contentPadding.calculateBottomPadding() + 28.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when {
                state.isLoading -> {
                    items(2) {
                        SkeletonBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 10f)
                        )
                    }
                }

                state.hasError -> {
                    item {
                        ErrorState(message = state.errorMessage.orEmpty())
                    }
                }

                state.isEmpty -> {
                    item {
                        EmptyState(
                            title = "Пока пусто",
                            description = "Нажимайте на сердечко у рецепта, чтобы сохранить его здесь.",
                            action = {
                                Button(onClick = onBrowse) { Text("К рецептам") }
                            }
                        )
                    }
                }

                else -> {
                    items(state.recipes, key = { it.id }) { recipe ->
                        WideRecipeCard(
                            recipe = recipe,
                            isFavorite = true,
                            onToggleFavorite = { onToggleFavorite(recipe.id) },
                            onClick = { onRecipeClick(recipe.id) }
                        )
                    }
                }
            }
        }
    }
}
