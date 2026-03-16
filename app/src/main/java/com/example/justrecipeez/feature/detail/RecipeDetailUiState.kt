package com.example.justrecipeez.feature.detail

import com.example.justrecipeez.domain.model.Recipe

data class RecipeDetailUiState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = true
)
