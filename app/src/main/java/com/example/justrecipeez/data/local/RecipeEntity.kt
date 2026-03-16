package com.example.justrecipeez.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val notes: String,
    val prepMinutes: Int?,
    val cookMinutes: Int?,
    val servings: Int?,
    val favorite: Boolean,
    val imageUri: String?,
    val createdUtc: Long,
    val updatedUtc: Long
)
