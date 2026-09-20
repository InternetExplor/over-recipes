package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.components.SkeletonBox

@Composable
fun RecipePreviewSkeleton(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            Modifier
                .padding(24.dp)
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            SkeletonBox(Modifier.fillMaxWidth(0.5f).height(20.dp))
            Spacer(Modifier.height(16.dp))
            SkeletonBox(Modifier.fillMaxWidth().height(40.dp))
            Spacer(Modifier.height(12.dp))
            SkeletonBox(Modifier.fillMaxWidth().height(72.dp))
            Spacer(Modifier.height(20.dp))
            SkeletonBox(Modifier.fillMaxWidth().height(56.dp))
        }
    }
}
