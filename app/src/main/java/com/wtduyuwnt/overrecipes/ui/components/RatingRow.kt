package com.wtduyuwnt.overrecipes.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.theme.RatingGold
import kotlin.math.floor

@Composable
fun RatingRow(
    rating: Float,
    modifier: Modifier = Modifier,
    trailing: String? = null,
    starSize: Dp = 15.dp,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    if (rating <= 0f) return

    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        val full = floor(rating.toDouble()).toInt()
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = when {
                    index < full -> RatingGold
                    index.toFloat() < rating -> RatingGold.copy(alpha = 0.55f)
                    else -> contentColor.copy(alpha = 0.25f)
                },
                modifier = Modifier.size(starSize)
            )
        }
        Spacer(Modifier.width(6.dp))
        Text(
            text = rating.toString().replace('.', ','),
            style = MaterialTheme.typography.labelMedium,
            color = contentColor
        )
        if (trailing != null) {
            Spacer(Modifier.width(6.dp))
            Text(
                text = trailing,
                style = MaterialTheme.typography.labelMedium,
                color = contentColor.copy(alpha = 0.8f)
            )
        }
    }
}
