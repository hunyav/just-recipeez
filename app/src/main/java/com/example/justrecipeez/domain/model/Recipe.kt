package com.example.justrecipeez.domain.model

data class Recipe(
    val id: Long,
    val title: String,
    val description: String,
    val tags: List<String>,
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
