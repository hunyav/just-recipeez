package com.example.justrecipeez.feature.edit

data class EditRecipeUiState(
    val id: Long? = null,
    val title: String = "",
    val description: String = "",
    val tags: String = "",
    val ingredients: String = "",
    val instructions: String = "",
    val notes: String = "",
    val prepMinutes: String = "",
    val cookMinutes: String = "",
    val servings: String = "",
    val favorite: Boolean = false,
    val imageUri: String = "",
    val isLoading: Boolean = true,
    val canSave: Boolean = false
)
