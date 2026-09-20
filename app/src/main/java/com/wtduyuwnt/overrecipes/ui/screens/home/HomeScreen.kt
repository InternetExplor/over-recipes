package com.wtduyuwnt.overrecipes.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.components.EmptyState
import com.wtduyuwnt.overrecipes.ui.components.ErrorState
import com.wtduyuwnt.overrecipes.ui.components.FeaturedRecipeCard
import com.wtduyuwnt.overrecipes.ui.components.RecipeListCard
import com.wtduyuwnt.overrecipes.ui.components.SectionHeader
import com.wtduyuwnt.overrecipes.ui.components.SkeletonBox
import com.wtduyuwnt.overrecipes.ui.screens.home.components.CategoryFilterRow
import com.wtduyuwnt.overrecipes.ui.screens.home.components.HomeTopBar
import com.wtduyuwnt.overrecipes.ui.screens.home.components.RecipeSearchField
import com.wtduyuwnt.overrecipes.ui.state.HomeUiState

@Composable
fun HomeScreen(
    state: HomeUiState,
    onQueryChange: (String) -> Unit,
    onCategorySelect: (String) -> Unit,
    onResetFilters: () -> Unit,
    onRetry: () -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    contentPadding: PaddingValues
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HomeTopBar(
            title = "OSHXONA",
            subtitle = "Что-нибудь на ужин?",
            onProfileClick = onProfileClick
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = contentPadding.calculateBottomPadding() + 28.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    text = "Что приготовим\nсегодня?",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item {
                RecipeSearchField(
                    query = state.query,
                    onQueryChange = onQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }

            item {
                CategoryFilterRow(
                    categories = state.categories,
                    selectedCategory = state.selectedCategory,
                    onCategorySelect = onCategorySelect
                )
            }

            if (state.showFeatured) {
                item {
                    SectionHeader(
                        title = "Рекомендуем",
                        trailing = "Топ недели",
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(state.featured, key = { it.id }) { recipe ->
                            FeaturedRecipeCard(
                                recipe = recipe,
                                isFavorite = state.favorites.contains(recipe.id),
                                onToggleFavorite = { onToggleFavorite(recipe.id) },
                                onClick = { onRecipeClick(recipe.id) }
                            )
                        }
                    }
                }
            }

            item {
                SectionHeader(
                    title = if (state.isSearching) "Найдено" else "Все рецепты",
                    trailing = if (state.isLoading || state.hasError) "" else "${state.recipes.size}",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            when {
                state.isLoading -> {
                    items(4) {
                        SkeletonBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(116.dp)
                                .padding(horizontal = 20.dp)
                        )
                    }
                }

                state.queryTooShort -> {
                    item {
                        EmptyState(
                            title = "Продолжайте вводить",
                            description = "Для поиска нужно минимум 2 символа."
                        )
                    }
                }

                state.hasError -> {
                    item {
                        ErrorState(
                            message = state.errorMessage.orEmpty(),
                            onRetry = onRetry
                        )
                    }
                }

                state.isEmpty -> {
                    item {
                        EmptyState(
                            title = "Ничего не нашлось",
                            description = "Попробуйте другой запрос или выберите категорию «Все».",
                            action = {
                                TextButton(onClick = onResetFilters) {
                                    Text("Сбросить фильтры")
                                }
                            }
                        )
                    }
                }

                else -> {
                    items(state.recipes, key = { it.id }) { recipe ->
                        RecipeListCard(
                            recipe = recipe,
                            isFavorite = state.favorites.contains(recipe.id),
                            onToggleFavorite = { onToggleFavorite(recipe.id) },
                            onClick = { onRecipeClick(recipe.id) },
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(4.dp)) }
        }
    }
}
