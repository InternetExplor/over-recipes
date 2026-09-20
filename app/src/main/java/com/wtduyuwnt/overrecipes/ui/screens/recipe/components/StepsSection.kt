package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.data.model.CookStep
import com.wtduyuwnt.overrecipes.util.formatMinutes

@Composable
fun StepsSection(
    steps: List<CookStep>,
    completedSteps: Set<Int>,
    progress: Float,
    onStepToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(28.dp))
        Text(
            text = "Приготовление",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(12.dp))
        if (steps.isEmpty()) {
            Text(
                text = "Шаги приготовления пока недоступны.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            return@Column
        }
        Text(
            text = "Готово ${completedSteps.size} из ${steps.size}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            trackColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
        Spacer(Modifier.height(20.dp))

        steps.forEachIndexed { index, step ->
            val done = completedSteps.contains(index)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { onStepToggle(index) }
                    .padding(vertical = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            if (done) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceContainerHigh
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (done) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    } else {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text = step.text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (done) MaterialTheme.colorScheme.onSurfaceVariant
                        else MaterialTheme.colorScheme.onSurface,
                        textDecoration = if (done) TextDecoration.LineThrough else null
                    )
                    val minutes = step.minutes
                    if (minutes != null) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "≈ ${formatMinutes(minutes)}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
