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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val queryFlow = MutableStateFlow("")
    private val syncState = MutableStateFlow(SyncUiState())

    val uiState: StateFlow<RecipeListUiState> = queryFlow
        .flatMapLatest { query -> repository.observeRecipes(query) }
        .combine(queryFlow) { recipes, query -> recipes to query }
        .combine(syncState) { (recipes, query), sync ->
            RecipeListUiState(
                query = query,
                recipes = recipes,
                isLoading = false,
                isSyncing = sync.isSyncing,
                syncMessage = sync.message
            )
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

    fun importFromBackup() {
        viewModelScope.launch {
            syncState.update { it.copy(isSyncing = true, message = null) }
            runCatching { repository.importFromBackup() }
                .onSuccess { count ->
                    syncState.update { SyncUiState(isSyncing = false, message = "Imported $count recipes") }
                }
                .onFailure { error ->
                    syncState.update { SyncUiState(isSyncing = false, message = error.message ?: "Import failed") }
                }
        }
    }

    fun exportToBackup() {
        viewModelScope.launch {
            syncState.update { it.copy(isSyncing = true, message = null) }
            runCatching { repository.exportToBackup() }
                .onSuccess { count ->
                    syncState.update { SyncUiState(isSyncing = false, message = "Exported $count recipes") }
                }
                .onFailure { error ->
                    syncState.update { SyncUiState(isSyncing = false, message = error.message ?: "Export failed") }
                }
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

data class SyncUiState(
    val isSyncing: Boolean = false,
    val message: String? = null
)
