package com.example.justrecipeez.feature.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(
    uiState: EditRecipeUiState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onIngredientsChange: (String) -> Unit,
    onInstructionsChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onPrepMinutesChange: (String) -> Unit,
    onCookMinutesChange: (String) -> Unit,
    onServingsChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.id == null) "Add Recipe" else "Edit Recipe") },
                navigationIcon = { TextButton(onClick = onBackClick) { Text("Cancel") } }
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(value = uiState.title, onValueChange = onTitleChange, label = { Text("Title") })
            OutlinedTextField(value = uiState.description, onValueChange = onDescriptionChange, label = { Text("Description") })
            OutlinedTextField(value = uiState.ingredients, onValueChange = onIngredientsChange, label = { Text("Ingredients") })
            OutlinedTextField(value = uiState.instructions, onValueChange = onInstructionsChange, label = { Text("Instructions") })
            OutlinedTextField(value = uiState.notes, onValueChange = onNotesChange, label = { Text("Notes") })
            OutlinedTextField(value = uiState.prepMinutes, onValueChange = onPrepMinutesChange, label = { Text("Prep minutes") })
            OutlinedTextField(value = uiState.cookMinutes, onValueChange = onCookMinutesChange, label = { Text("Cook minutes") })
            OutlinedTextField(value = uiState.servings, onValueChange = onServingsChange, label = { Text("Servings") })
            Button(onClick = onSaveClick, enabled = uiState.canSave) {
                Text("Save")
            }
        }
    }
}
