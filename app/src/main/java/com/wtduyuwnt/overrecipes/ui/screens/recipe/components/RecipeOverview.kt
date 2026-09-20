package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.data.model.Recipe
import com.wtduyuwnt.overrecipes.ui.components.MetaColumn
import com.wtduyuwnt.overrecipes.util.formatMinutesOrDash

@Composable
fun RecipeOverview(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(24.dp))
        if (recipe.summary.isNotBlank()) {
            Text(
                text = recipe.summary,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))
        }
        if (recipe.totalMinutes > 0) {
            TimingRow(recipe)
            Spacer(Modifier.height(20.dp))
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
private fun TimingRow(recipe: Recipe) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        MetaColumn(
            label = "Подготовка",
            value = formatMinutesOrDash(recipe.prepMinutes),
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier.height(34.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        MetaColumn(
            label = "Готовка",
            value = formatMinutesOrDash(recipe.cookMinutes),
            alignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier.height(34.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        MetaColumn(
            label = "Всего",
            value = formatMinutesOrDash(recipe.totalMinutes),
            alignment = Alignment.End,
            modifier = Modifier.weight(1f)
        )
    }
}
