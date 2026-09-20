package com.wtduyuwnt.overrecipes.ui.screens.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.components.EmptyState
import com.wtduyuwnt.overrecipes.ui.components.ErrorState
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.ChefTipCard
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.IngredientsSection
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.RecipeDetailBottomBar
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.RecipeDetailHeader
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.RecipeDetailSkeleton
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.RecipeDetailTopBar
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.RecipeOverview
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.RecipeVideoSection
import com.wtduyuwnt.overrecipes.ui.screens.recipe.components.StepsSection
import com.wtduyuwnt.overrecipes.ui.state.RecipeUiState
import kotlinx.coroutines.launch

private const val COLLAPSE_DISTANCE_PX = 520f
private const val VIDEO_ITEM_INDEX = 2

@Composable
fun RecipeDetailScreen(
    state: RecipeUiState,
    onToggleFavorite: () -> Unit,
    onServingsChange: (Int) -> Unit,
    onStepToggle: (Int) -> Unit,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val collapse by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex > 0) 1f
            else (listState.firstVisibleItemScrollOffset / COLLAPSE_DISTANCE_PX).coerceIn(0f, 1f)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            val recipe = state.recipe
            if (recipe != null) {
                RecipeDetailBottomBar(
                    isFavorite = state.isFavorite,
                    onToggleFavorite = onToggleFavorite,
                    onStartCooking = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Приятного аппетита! Отмечайте шаги по мере готовки"
                            )
                        }
                    }
                )
            }
        }
    ) { padding ->
        val recipe = state.recipe
        when {
            state.isLoading -> RecipeDetailSkeleton()

            state.hasError -> ErrorState(
                message = state.errorMessage.orEmpty(),
                modifier = Modifier.fillMaxSize(),
                onRetry = onRetry
            )

            recipe == null -> EmptyState(
                title = "Рецепт не найден",
                description = "Возможно, он был удалён с сервера.",
                modifier = Modifier.fillMaxSize(),
                action = { TextButton(onClick = onBack) { Text("Назад") } }
            )

            else -> Box(Modifier.fillMaxSize()) {
                val playVideo: (() -> Unit)? = if (recipe.hasVideo) {
                    { scope.launch { listState.animateScrollToItem(VIDEO_ITEM_INDEX) } }
                } else {
                    null
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        bottom = padding.calculateBottomPadding() + 24.dp
                    )
                ) {
                    item {
                        RecipeDetailHeader(
                            recipe = recipe,
                            onPlayVideo = playVideo,
                            parallax = {
                                if (listState.firstVisibleItemIndex == 0) {
                                    listState.firstVisibleItemScrollOffset * 0.35f
                                } else 0f
                            }
                        )
                    }

                    item { RecipeOverview(recipe) }

                    if (recipe.hasVideo) {
                        item {
                            RecipeVideoSection(videoUrl = recipe.videoUrl.orEmpty())
                        }
                    }

                    item {
                        IngredientsSection(
                            ingredients = state.ingredients,
                            servings = state.servings,
                            caloriesPerServing = recipe.calories,
                            onServingsChange = onServingsChange
                        )
                    }

                    item {
                        StepsSection(
                            steps = recipe.steps,
                            completedSteps = state.completedSteps,
                            progress = state.progress,
                            onStepToggle = onStepToggle
                        )
                    }

                    val tip = recipe.tip
                    if (tip != null) {
                        item { ChefTipCard(tip) }
                    }
                }

                RecipeDetailTopBar(
                    title = recipe.title,
                    collapse = collapse,
                    onBack = onBack
                )
            }
        }
    }
}
