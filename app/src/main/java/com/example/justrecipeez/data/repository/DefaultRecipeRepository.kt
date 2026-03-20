package com.example.justrecipeez.data.repository

import com.example.justrecipeez.data.backup.BackupApi
import com.example.justrecipeez.data.backup.BackupRecipeDto
import com.example.justrecipeez.data.local.RecipeDao
import com.example.justrecipeez.data.local.toDomain
import com.example.justrecipeez.data.local.toEntity
import com.example.justrecipeez.domain.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultRecipeRepository(
    private val dao: RecipeDao,
    private val backupApi: BackupApi
) : RecipeRepository {
    override fun observeRecipes(searchQuery: String): Flow<List<Recipe>> {
        val source = if (searchQuery.isBlank()) dao.observeRecipes() else dao.searchRecipes(searchQuery.trim())
        return source.map { list -> list.map { it.toDomain() } }
    }

    override fun observeRecipe(id: Long): Flow<Recipe?> = dao.observeRecipe(id).map { it?.toDomain() }

    override suspend fun getRecipe(id: Long): Recipe? = dao.getRecipe(id)?.toDomain()

    override suspend fun create(recipe: Recipe): Long = dao.insert(recipe.toEntity())

    override suspend fun update(recipe: Recipe) {
        dao.update(recipe.toEntity())
    }

    override suspend fun delete(id: Long) {
        dao.getRecipe(id)?.let { dao.delete(it) }
    }

    override suspend fun setFavorite(id: Long, favorite: Boolean, updatedUtc: Long) {
        dao.updateFavorite(id, favorite, updatedUtc)
    }

    override suspend fun importFromBackup(): Int = withContext(Dispatchers.IO) {
        val imported = backupApi.fetchLatestRecipes().map { it.toEntity() }
        dao.deleteAll()
        dao.insertAll(imported)
        imported.size
    }

    override suspend fun exportToBackup(): Int = withContext(Dispatchers.IO) {
        val local = dao.getAllRecipes()
        backupApi.postBackup(local.map(BackupRecipeDto::fromEntity))
        local.size
    }
}
