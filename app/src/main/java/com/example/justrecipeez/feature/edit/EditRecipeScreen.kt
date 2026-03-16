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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
            RecipeImage(
                imageUri = uiState.imageUri,
                contentDescription = "Recipe image preview",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        pickImageLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Text("Gallery")
                }
                OutlinedButton(onClick = { cameraLauncher.launch(null) }) {
                    Text("Camera")
                }
                if (uiState.imageUri.isNotBlank()) {
                    OutlinedButton(onClick = { onImageUriChange("") }) {
                        Text("Remove")
                    }
                }
            }

            OutlinedTextField(
                value = uiState.title,
                onValueChange = onTitleChange,
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.ingredients,
                onValueChange = onIngredientsChange,
                label = { Text("Ingredients (one per line)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.instructions,
                onValueChange = onInstructionsChange,
                label = { Text("Instructions (one per line)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.notes,
                onValueChange = onNotesChange,
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.prepMinutes,
                onValueChange = onPrepMinutesChange,
                label = { Text("Prep minutes") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.cookMinutes,
                onValueChange = onCookMinutesChange,
                label = { Text("Cook minutes") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.servings,
                onValueChange = onServingsChange,
                label = { Text("Servings") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = onSaveClick, enabled = uiState.canSave) {
                Text("Save")
            }
        }
    }
}
