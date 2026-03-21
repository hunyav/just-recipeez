package com.example.justrecipeez.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.justrecipeez.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun importFromBackup() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSyncing = true, message = null) }
            runCatching { repository.importFromBackup() }
                .onSuccess { count ->
                    _uiState.update { SettingsUiState(isSyncing = false, message = "Imported $count recipes") }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        SettingsUiState(
                            isSyncing = false,
                            message = "Import failed: ${throwable.message ?: "Unknown error"}"
                        )
                    }
                }
        }
    }

    fun exportToBackup() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSyncing = true, message = null) }
            runCatching { repository.exportToBackup() }
                .onSuccess { count ->
                    _uiState.update { SettingsUiState(isSyncing = false, message = "Exported $count recipes") }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        SettingsUiState(
                            isSyncing = false,
                            message = "Export failed: ${throwable.message ?: "Unknown error"}"
                        )
                    }
                }
        }
    }

    companion object {
        fun factory(repository: RecipeRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SettingsViewModel(repository) as T
                }
            }
    }
}
