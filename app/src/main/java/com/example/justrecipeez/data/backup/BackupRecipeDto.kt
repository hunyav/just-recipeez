package com.example.justrecipeez.data.backup

import com.example.justrecipeez.data.local.RecipeEntity
import org.json.JSONObject

data class BackupRecipeDto(
    val title: String,
    val description: String,
    val ingredients: String,
    val instructions: String,
    val notes: String,
    val prepMinutes: Int?,
    val cookMinutes: Int?,
    val servings: Int?,
    val favorite: Boolean,
    val imageUri: String?,
    val createdUtc: Long,
    val updatedUtc: Long
) {
    fun toEntity(): RecipeEntity = RecipeEntity(
        id = 0,
        title = title,
        description = description,
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

    fun toJson(): JSONObject = JSONObject()
        .put("title", title)
        .put("description", description)
        .put("ingredients", ingredients)
        .put("instructions", instructions)
        .put("notes", notes)
        .put("prepMinutes", prepMinutes)
        .put("cookMinutes", cookMinutes)
        .put("servings", servings)
        .put("favorite", favorite)
        .put("imageUri", imageUri)
        .put("createdUtc", createdUtc)
        .put("updatedUtc", updatedUtc)

    companion object {
        fun fromEntity(entity: RecipeEntity): BackupRecipeDto = BackupRecipeDto(
            title = entity.title,
            description = entity.description,
            ingredients = entity.ingredients,
            instructions = entity.instructions,
            notes = entity.notes,
            prepMinutes = entity.prepMinutes,
            cookMinutes = entity.cookMinutes,
            servings = entity.servings,
            favorite = entity.favorite,
            imageUri = entity.imageUri,
            createdUtc = entity.createdUtc,
            updatedUtc = entity.updatedUtc
        )

        fun fromJson(json: JSONObject): BackupRecipeDto = BackupRecipeDto(
            title = json.optString("title"),
            description = json.optString("description"),
            ingredients = json.optString("ingredients"),
            instructions = json.optString("instructions"),
            notes = json.optString("notes"),
            prepMinutes = json.optIntOrNull("prepMinutes"),
            cookMinutes = json.optIntOrNull("cookMinutes"),
            servings = json.optIntOrNull("servings"),
            favorite = json.optBoolean("favorite", false),
            imageUri = json.optString("imageUri").takeIf { it.isNotBlank() },
            createdUtc = json.optLong("createdUtc", System.currentTimeMillis()),
            updatedUtc = json.optLong("updatedUtc", System.currentTimeMillis())
        )

        private fun JSONObject.optIntOrNull(key: String): Int? {
            if (!has(key) || isNull(key)) return null
            return optInt(key)
        }
    }
}
