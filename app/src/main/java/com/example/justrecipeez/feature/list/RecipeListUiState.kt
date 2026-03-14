package com.example.justrecipeez.feature.list

import com.example.justrecipeez.domain.model.Recipe

data class RecipeListUiState(
    val query: String = "",
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = true
)
