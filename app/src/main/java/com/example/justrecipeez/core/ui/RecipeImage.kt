package com.example.justrecipeez.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun RecipeImage(
    imageUri: String?, contentDescription: String, modifier: Modifier = Modifier
) {
    val shapedModifier = modifier.clip(MaterialTheme.shapes.medium)
    if (imageUri.isNullOrBlank()) {
        PlaceholderRecipeImage(modifier = shapedModifier)
        return
    }

    AsyncImage(
        model = imageUri,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = shapedModifier
    )
}

@Composable
private fun PlaceholderRecipeImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Restaurant,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(28.dp)
        )
    }
}
