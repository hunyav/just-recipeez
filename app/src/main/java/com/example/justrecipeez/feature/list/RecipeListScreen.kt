package com.example.justrecipeez.feature.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.justrecipeez.core.ui.RecipeImage
import com.example.justrecipeez.domain.model.Recipe

@Composable
fun RecipeListScreen(
    uiState: RecipeListUiState,
    onQueryChange: (String) -> Unit,
    onRecipeClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    onFavoriteToggle: (Long, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add recipe")
            }
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.query,
                onValueChange = onQueryChange,
                label = { Text("Search recipes (title, description, ingredients, tags)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            LazyColumn(
                contentPadding = PaddingValues(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = uiState.recipes, key = { it.id }) { recipe ->
                    RecipeRow(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id) },
                        onFavoriteToggle = { onFavoriteToggle(recipe.id, !recipe.favorite) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RecipeRow(
    recipe: Recipe,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RecipeImage(
            imageUri = recipe.imageUri,
            contentDescription = recipe.title,
            modifier = Modifier.size(64.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleMedium)
            if (recipe.tags.isNotEmpty()) {
                Text(text = recipe.tags.joinToString(" • "), style = MaterialTheme.typography.bodySmall)
            }
            if (recipe.description.isNotBlank()) {
                Text(text = recipe.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
        IconButton(onClick = onFavoriteToggle) {
            Icon(
                imageVector = if (recipe.favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite toggle"
            )
        }
    }
}
