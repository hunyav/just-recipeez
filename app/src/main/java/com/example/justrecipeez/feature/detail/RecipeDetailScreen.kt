package com.example.justrecipeez.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
    val checkedMap = remember(recipe?.id) { mutableStateMapOf<String, Boolean>() }

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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
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
                recipe.ingredients.forEachIndexed { index, line ->
                    CheckableLine(
                        key = "ingredient-$index",
                        text = line,
                        checkedMap = checkedMap
                    )
                }

                Text("Instructions", style = MaterialTheme.typography.titleMedium)
                recipe.instructions.forEachIndexed { index, line ->
                    CheckableLine(
                        key = "instruction-$index",
                        text = line,
                        checkedMap = checkedMap
                    )
                }

                if (recipe.notes.isNotBlank()) {
                    Text("Notes", style = MaterialTheme.typography.titleMedium)
                    Text(recipe.notes)
                }
            }
        }
    }
}

@Composable
private fun CheckableLine(
    key: String,
    text: String,
    checkedMap: MutableMap<String, Boolean>
) {
    val checked = checkedMap[key] ?: false
    Row(verticalAlignment = Alignment.Top) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checkedMap[key] = it }
        )
        Text(text = text, modifier = Modifier.padding(top = 12.dp))
    }
}
