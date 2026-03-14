package com.example.justrecipeez.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.justrecipeez.core.util.TimeProvider
import com.example.justrecipeez.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val queryFlow = MutableStateFlow("")

    val uiState: StateFlow<RecipeListUiState> = queryFlow
        .flatMapLatest { query ->
            repository.observeRecipes(query)
                .combine(queryFlow) { recipes, currentQuery ->
                    RecipeListUiState(query = currentQuery, recipes = recipes, isLoading = false)
                }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), RecipeListUiState())

    fun onQueryChange(query: String) {
        queryFlow.value = query
    }

    fun onFavoriteToggle(recipeId: Long, favorite: Boolean) {
        viewModelScope.launch {
            repository.setFavorite(recipeId, favorite, TimeProvider.nowUtcMillis())
        }
    }

    companion object {
        fun factory(repository: RecipeRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeListViewModel(repository) as T
                }
            }
    }
}
