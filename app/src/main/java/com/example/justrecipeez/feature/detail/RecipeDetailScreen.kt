package com.example.justrecipeez.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    RecipeImage(
                        imageUri = recipe.imageUri,
                        contentDescription = recipe.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    )
                }

                if (recipe.description.isNotBlank()) {
                    Text(recipe.description, style = MaterialTheme.typography.bodyLarge)
                }

                if (recipe.tags.isNotEmpty()) {
                    SectionHeader(text = "Tags")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        recipe.tags.forEach { tag ->
                            AssistChip(
                                onClick = {},
                                label = { Text(tag) },
                                colors = AssistChipDefaults.assistChipColors()
                            )
                        }
                    }
                }

                Divider()
                SectionHeader(text = "Ingredients")
                recipe.ingredients.forEachIndexed { index, line ->
                    CheckableLine(
                        key = "ingredient-$index",
                        text = line,
                        checkedMap = checkedMap
                    )
                }

                Divider()
                SectionHeader(text = "Instructions")
                recipe.instructions.forEachIndexed { index, line ->
                    CheckableLine(
                        key = "instruction-$index",
                        text = line,
                        checkedMap = checkedMap
                    )
                }

                if (recipe.notes.isNotBlank()) {
                    Divider()
                    SectionHeader(text = "Notes")
                    Text(recipe.notes)
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.Category, contentDescription = null)
        Text(text = text, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 8.dp))
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
