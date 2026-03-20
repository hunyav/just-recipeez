package com.example.justrecipeez.data.repository

import com.example.justrecipeez.data.local.RecipeDao
import com.example.justrecipeez.data.local.toDomain
import com.example.justrecipeez.data.local.toEntity
import com.example.justrecipeez.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultRecipeRepository(
    private val dao: RecipeDao
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
}
