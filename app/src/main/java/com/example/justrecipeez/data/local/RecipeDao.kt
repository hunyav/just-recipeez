package com.example.justrecipeez.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(ListStringConverters::class)
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY updatedUtc DESC")
    fun observeRecipes(): Flow<List<RecipeEntity>>

    @Query(
        """
        SELECT * FROM recipes
        WHERE title LIKE '%' || :query || '%'
           OR description LIKE '%' || :query || '%'
           OR tags LIKE '%' || :query || '%'
           OR ingredients LIKE '%' || :query || '%'
        ORDER BY updatedUtc DESC
        """
    )
    fun searchRecipes(query: String): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun observeRecipe(id: Long): Flow<RecipeEntity?>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipe(id: Long): RecipeEntity?

    @Query("SELECT * FROM recipes ORDER BY updatedUtc DESC")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<RecipeEntity>)

    @Update
    suspend fun update(entity: RecipeEntity)

    @Delete
    suspend fun delete(entity: RecipeEntity)

    @Query("DELETE FROM recipes")
    suspend fun deleteAll()

    @Query("UPDATE recipes SET favorite = :favorite, updatedUtc = :updatedUtc WHERE id = :id")
    suspend fun updateFavorite(id: Long, favorite: Boolean, updatedUtc: Long)
}
