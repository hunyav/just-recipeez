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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.justrecipeez.core.ui.RecipeImage
import com.example.justrecipeez.domain.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
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
        topBar = { TopAppBar(title = { Text("Just Recipeez") }) },
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
                label = { Text("Search recipes") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            LazyColumn(
                contentPadding = PaddingValues(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = uiState.recipes, key = { it.id }) { recipe ->
                    RecipeCard(
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
private fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RecipeImage(
                imageUri = recipe.imageUri,
                contentDescription = recipe.title,
                modifier = Modifier.size(72.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = recipe.title, style = MaterialTheme.typography.titleMedium)
                if (recipe.tags.isNotEmpty()) {
                    OneLineChipsRow(tags = recipe.tags)
                }
                if (recipe.description.isNotBlank()) {
                    Text(
                        text = recipe.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
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
}


@Composable
private fun OneLineChipsRow(
    tags: List<String>,
    spacing: androidx.compose.ui.unit.Dp = 6.dp,
    maxChips: Int = 6
) {
    if (tags.isEmpty()) return

    androidx.compose.ui.layout.SubcomposeLayout { constraints ->
        val maxWidthPx = constraints.maxWidth
        val spacePx = spacing.roundToPx()
        val items = tags.take(maxChips)

        val measured = items.mapIndexed { index, tag ->
            val measurables = subcompose("chip-$index") {
                AssistChip(
                    onClick = {},
                    label = { Text(tag, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    colors = AssistChipDefaults.assistChipColors()
                )
            }
            measurables.first().measure(constraints.copy(minWidth = 0))
        }

        val selected = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var usedWidth = 0
        measured.forEach { placeable ->
            val required = if (selected.isEmpty()) placeable.width else placeable.width + spacePx
            if (usedWidth + required <= maxWidthPx) {
                selected += placeable
                usedWidth += required
            }
        }

        val finalPlaceables: List<androidx.compose.ui.layout.Placeable>
        var finalWidth = usedWidth
        if (selected.isEmpty()) {
            val firstLabel = items.firstOrNull() ?: ""
            val fallback = subcompose("fallback") {
                AssistChip(
                    onClick = {},
                    label = { Text(firstLabel, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    colors = AssistChipDefaults.assistChipColors()
                )
            }.first().measure(constraints.copy(minWidth = 0, maxWidth = maxWidthPx))
            finalPlaceables = if (fallback.width <= maxWidthPx && fallback.height > 0) listOf(fallback) else emptyList()
            finalWidth = finalPlaceables.firstOrNull()?.width ?: 0
        } else {
            finalPlaceables = selected
        }

        val height = (finalPlaceables.maxOfOrNull { it.height } ?: 0).coerceAtLeast(constraints.minHeight)
        val width = finalWidth.coerceIn(constraints.minWidth, constraints.maxWidth)

        layout(width, height) {
            var x = 0
            finalPlaceables.forEachIndexed { i, p ->
                p.placeRelative(x, 0)
                x += p.width
                if (i != finalPlaceables.lastIndex) x += spacePx
            }
        }
    }
}
