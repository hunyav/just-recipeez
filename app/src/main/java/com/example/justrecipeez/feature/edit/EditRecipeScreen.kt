package com.example.justrecipeez.feature.edit

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.justrecipeez.core.ui.RecipeImage
import com.example.justrecipeez.core.util.ImageStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(
    uiState: EditRecipeUiState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onTagsChange: (String) -> Unit,
    onIngredientsChange: (String) -> Unit,
    onInstructionsChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onPrepMinutesChange: (String) -> Unit,
    onCookMinutesChange: (String) -> Unit,
    onServingsChange: (String) -> Unit,
    onImageUriChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.toString()?.let(onImageUriChange)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            onImageUriChange(ImageStorage.saveBitmap(context, it))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.id == null) "Add Recipe" else "Edit Recipe") },
                navigationIcon = { TextButton(onClick = onBackClick) { Text("Cancel") } },
                actions = {
                    IconButton(onClick = onSaveClick, enabled = uiState.canSave) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                    }
                }
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RecipeImage(
                imageUri = uiState.imageUri,
                contentDescription = "Recipe image preview",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        pickImageLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Icon(imageVector = Icons.Default.Image, contentDescription = null)
                    Text("  Gallery")
                }
                OutlinedButton(onClick = { cameraLauncher.launch(null) }) {
                    Icon(imageVector = Icons.Default.CameraAlt, contentDescription = null)
                    Text("  Camera")
                }
                if (uiState.imageUri.isNotBlank()) {
                    OutlinedButton(onClick = { onImageUriChange("") }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        Text("  Remove")
                    }
                }
            }

            SectionTitle("Basics")
            OutlinedTextField(
                value = uiState.title,
                onValueChange = onTitleChange,
                label = { Text("Title") },
                placeholder = { Text("E.g., Spaghetti Bolognese") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChange,
                label = { Text("Description") },
                placeholder = { Text("A short summary of the dish") },
                modifier = Modifier.fillMaxWidth()
            )

            SectionTitle("Tags")
            OutlinedTextField(
                value = uiState.tags,
                onValueChange = onTagsChange,
                label = { Text("Tags (one per line)") },
                placeholder = { Text("Italian\nPasta\nWeeknight") },
                modifier = Modifier.fillMaxWidth()
            )

            SectionTitle("Ingredients")
            OutlinedTextField(
                value = uiState.ingredients,
                onValueChange = onIngredientsChange,
                label = { Text("Ingredients (one per line)") },
                placeholder = { Text("2 cups flour\n1 tsp salt\n...") },
                modifier = Modifier.fillMaxWidth()
            )

            SectionTitle("Instructions")
            OutlinedTextField(
                value = uiState.instructions,
                onValueChange = onInstructionsChange,
                label = { Text("Instructions (one per line)") },
                placeholder = { Text("Preheat oven to 180°C\nMix ingredients\n...") },
                modifier = Modifier.fillMaxWidth()
            )

            SectionTitle("Notes")
            OutlinedTextField(
                value = uiState.notes,
                onValueChange = onNotesChange,
                label = { Text("Notes") },
                placeholder = { Text("Serving suggestions, substitutions, etc.") },
                modifier = Modifier.fillMaxWidth()
            )

            SectionTitle("Time & Servings")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = uiState.prepMinutes,
                    onValueChange = onPrepMinutesChange,
                    label = { Text("Prep (min)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = uiState.cookMinutes,
                    onValueChange = onCookMinutesChange,
                    label = { Text("Cook (min)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = uiState.servings,
                    onValueChange = onServingsChange,
                    label = { Text("Servings") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
}
