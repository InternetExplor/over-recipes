package com.wtduyuwnt.overrecipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun RecipeImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    val placeholder = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.surfaceContainerHigh,
            MaterialTheme.colorScheme.surfaceContainerLow
        )
    )
    Box(modifier.background(placeholder)) {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
