package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.state.ScaledIngredient
import com.wtduyuwnt.overrecipes.util.joinMeta

@Composable
fun IngredientsSection(
    ingredients: List<ScaledIngredient>,
    servings: Int,
    caloriesPerServing: Int,
    onServingsChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = "Ингредиенты",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                val subtitle = joinMeta(
                    "${ingredients.size} позиций",
                    caloriesPerServing.takeIf { it > 0 }?.let { "$it ккал на порцию" }
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            ServingsStepper(servings = servings, onChange = onServingsChange)
        }
        Spacer(Modifier.height(16.dp))
        if (ingredients.isEmpty()) {
            Text(
                text = "Сервер не прислал список ингредиентов для этого рецепта.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        ingredients.forEach { ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(Modifier.width(14.dp))
                Text(
                    text = ingredient.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                val amount = ingredient.amount
                if (amount != null) {
                    Text(
                        text = amount,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )
        }
    }
}
