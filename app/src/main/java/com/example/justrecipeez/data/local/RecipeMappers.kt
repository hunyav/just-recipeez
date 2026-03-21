package com.example.justrecipeez.data.local

import com.example.justrecipeez.domain.model.Recipe

fun RecipeEntity.toDomain(): Recipe = Recipe(
    id = id,
    title = title,
    description = description,
    tags = tags,
    ingredients = ingredients,
    instructions = instructions,
    notes = notes,
    prepMinutes = prepMinutes,
    cookMinutes = cookMinutes,
    servings = servings,
    favorite = favorite,
    imageUri = imageUri,
    createdUtc = createdUtc,
    updatedUtc = updatedUtc
)

fun Recipe.toEntity(): RecipeEntity = RecipeEntity(
    id = id,
    title = title,
    description = description,
    tags = tags,
    ingredients = ingredients,
    instructions = instructions,
    notes = notes,
    prepMinutes = prepMinutes,
    cookMinutes = cookMinutes,
    servings = servings,
    favorite = favorite,
    imageUri = imageUri,
    createdUtc = createdUtc,
    updatedUtc = updatedUtc
)
