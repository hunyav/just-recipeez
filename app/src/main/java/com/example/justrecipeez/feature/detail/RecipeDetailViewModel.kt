package com.example.justrecipeez.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.justrecipeez.core.util.TimeProvider
import com.example.justrecipeez.data.repository.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeId: Long,
    private val repository: RecipeRepository
) : ViewModel() {

    val uiState: StateFlow<RecipeDetailUiState> = repository.observeRecipe(recipeId)
        .map { recipe -> RecipeDetailUiState(recipe = recipe, isLoading = false) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), RecipeDetailUiState())

    fun onFavoriteToggle() {
        val recipe = uiState.value.recipe ?: return
        viewModelScope.launch {
            repository.setFavorite(recipe.id, !recipe.favorite, TimeProvider.nowUtcMillis())
        }
    }

    fun onDelete(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.delete(recipeId)
            onComplete()
        }
    }

    companion object {
        fun factory(recipeId: Long, repository: RecipeRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeDetailViewModel(recipeId, repository) as T
                }
            }
    }
}
