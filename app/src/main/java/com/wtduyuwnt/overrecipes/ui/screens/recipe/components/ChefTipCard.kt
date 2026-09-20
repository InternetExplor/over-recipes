package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChefTipCard(
    tip: String,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(20.dp))
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(18.dp)) {
                Text(
                    text = "СОВЕТ ШЕФА",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = tip,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}
