package com.example.justrecipeez.data.repository

import com.example.justrecipeez.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun observeRecipes(searchQuery: String): Flow<List<Recipe>>
    fun observeRecipe(id: Long): Flow<Recipe?>
    suspend fun getRecipe(id: Long): Recipe?
    suspend fun create(recipe: Recipe): Long
    suspend fun update(recipe: Recipe)
    suspend fun delete(id: Long)
    suspend fun setFavorite(id: Long, favorite: Boolean, updatedUtc: Long)
    suspend fun importFromBackup(): Int
    suspend fun exportToBackup(): Int
}
