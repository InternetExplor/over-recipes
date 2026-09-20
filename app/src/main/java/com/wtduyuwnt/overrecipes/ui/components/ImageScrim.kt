package com.wtduyuwnt.overrecipes.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun imageScrim(): Brush = Brush.verticalGradient(
    0f to Color.Transparent,
    0.45f to Color.Black.copy(alpha = 0.25f),
    1f to Color.Black.copy(alpha = 0.85f)
)
