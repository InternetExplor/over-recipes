package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.components.SkeletonBox

@Composable
fun RecipeDetailSkeleton(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        SkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(RecipeHeaderHeight),
            shape = MaterialTheme.shapes.extraSmall
        )
        Column(Modifier.padding(20.dp)) {
            SkeletonBox(Modifier.fillMaxWidth().height(24.dp))
            Spacer(Modifier.height(12.dp))
            SkeletonBox(Modifier.fillMaxWidth(0.7f).height(24.dp))
            Spacer(Modifier.height(24.dp))
            SkeletonBox(Modifier.fillMaxWidth().height(80.dp))
        }
    }
}
