package com.example.justrecipeez.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.justrecipeez.core.ui.RecipeImage

@Composable
fun RecipeDetailScreen(
    uiState: RecipeDetailUiState,
    onBackClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val recipe = uiState.recipe
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.title ?: "Recipe") },
                navigationIcon = { TextButton(onClick = onBackClick) { Text("Back") } },
                actions = {
                    if (recipe != null) {
                        IconButton(onClick = onFavoriteToggle) {
                            Icon(
                                imageVector = if (recipe.favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Toggle favorite"
                            )
                        }
                        IconButton(onClick = { onEditClick(recipe.id) }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = onDeleteClick) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->
        if (recipe == null) {
            Text("Recipe not found", modifier = Modifier.padding(padding).padding(16.dp))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RecipeImage(
                    imageUri = recipe.imageUri,
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
                Text(recipe.description, style = MaterialTheme.typography.bodyLarge)
                Text("Ingredients", style = MaterialTheme.typography.titleMedium)
                Text(recipe.ingredients)
                Text("Instructions", style = MaterialTheme.typography.titleMedium)
                Text(recipe.instructions)
                if (recipe.notes.isNotBlank()) {
                    Text("Notes", style = MaterialTheme.typography.titleMedium)
                    Text(recipe.notes)
                }
            }
        }
    }
}
