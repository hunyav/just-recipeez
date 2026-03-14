package com.example.justrecipeez.feature.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.justrecipeez.core.util.TimeProvider
import com.example.justrecipeez.data.repository.RecipeRepository
import com.example.justrecipeez.domain.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditRecipeViewModel(
    private val recipeId: Long?,
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditRecipeUiState())
    val uiState: StateFlow<EditRecipeUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        if (recipeId == null) {
            _uiState.value = EditRecipeUiState(isLoading = false)
            return
        }
        viewModelScope.launch {
            val recipe = repository.getRecipe(recipeId)
            _uiState.value = if (recipe == null) {
                EditRecipeUiState(isLoading = false)
            } else {
                EditRecipeUiState(
                    id = recipe.id,
                    title = recipe.title,
                    description = recipe.description,
                    ingredients = recipe.ingredients,
                    instructions = recipe.instructions,
                    notes = recipe.notes,
                    prepMinutes = recipe.prepMinutes?.toString().orEmpty(),
                    cookMinutes = recipe.cookMinutes?.toString().orEmpty(),
                    servings = recipe.servings?.toString().orEmpty(),
                    favorite = recipe.favorite,
                    imageUri = recipe.imageUri.orEmpty(),
                    isLoading = false,
                    canSave = recipe.title.isNotBlank()
                )
            }
        }
    }

    fun onTitleChange(value: String) = update { copy(title = value.trimStart()) }
    fun onDescriptionChange(value: String) = update { copy(description = value) }
    fun onIngredientsChange(value: String) = update { copy(ingredients = value) }
    fun onInstructionsChange(value: String) = update { copy(instructions = value) }
    fun onNotesChange(value: String) = update { copy(notes = value) }
    fun onPrepMinutesChange(value: String) = update { copy(prepMinutes = value.filterDigits()) }
    fun onCookMinutesChange(value: String) = update { copy(cookMinutes = value.filterDigits()) }
    fun onServingsChange(value: String) = update { copy(servings = value.filterDigits()) }

    fun save(onComplete: (Long) -> Unit) {
        val state = uiState.value
        if (state.title.isBlank()) return

        viewModelScope.launch {
            val now = TimeProvider.nowUtcMillis()
            val recipe = Recipe(
                id = state.id ?: 0,
                title = state.title.trim(),
                description = state.description.trim(),
                ingredients = state.ingredients.trim(),
                instructions = state.instructions.trim(),
                notes = state.notes.trim(),
                prepMinutes = state.prepMinutes.toIntOrNull(),
                cookMinutes = state.cookMinutes.toIntOrNull(),
                servings = state.servings.toIntOrNull(),
                favorite = state.favorite,
                imageUri = state.imageUri.ifBlank { null },
                createdUtc = now,
                updatedUtc = now
            )

            val resultingId = if (state.id == null) {
                repository.create(recipe)
            } else {
                val existing = repository.getRecipe(state.id)
                repository.update(recipe.copy(createdUtc = existing?.createdUtc ?: now))
                state.id
            }

            onComplete(resultingId)
        }
    }

    private fun update(transform: EditRecipeUiState.() -> EditRecipeUiState) {
        _uiState.update { old ->
            val new = old.transform()
            new.copy(canSave = new.title.isNotBlank())
        }
    }

    private fun String.filterDigits(): String = filter { it.isDigit() }

    companion object {
        fun factory(recipeId: Long?, repository: RecipeRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EditRecipeViewModel(recipeId, repository) as T
                }
            }
    }
}
