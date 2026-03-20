package com.example.justrecipeez.data.backup

import android.os.Build
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class BackupApi(
    private val baseUrl: String,
    private val token: String,
    private val client: OkHttpClient = OkHttpClient()
) {
    fun fetchLatestRecipes(): List<BackupRecipeDto> {
        ensureConfigured()
        val request = Request.Builder()
            .url("${baseUrl.trimEnd('/')}/api/backups/latest")
            .header("Authorization", "Bearer $token")
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                error("Import failed: ${response.code}")
            }
            val body = response.body?.string().orEmpty()
            val root = JSONObject(body)
            val recipes = root.optJSONObject("payload")?.optJSONArray("recipes") ?: JSONArray()
            return (0 until recipes.length()).map { index ->
                BackupRecipeDto.fromJson(recipes.getJSONObject(index))
            }
        }
    }

    fun postBackup(recipes: List<BackupRecipeDto>) {
        ensureConfigured()
        val recipesArray = JSONArray().apply {
            recipes.forEach { put(it.toJson()) }
        }

        val root = JSONObject()
            .put("schemaVersion", 1)
            .put("source", "just-recipeez")
            .put("deviceName", Build.DEVICE)
            .put("payload", JSONObject().put("recipes", recipesArray))

        val request = Request.Builder()
            .url("${baseUrl.trimEnd('/')}/api/backups")
            .header("Authorization", "Bearer $token")
            .header("Content-Type", "application/json")
            .post(root.toString().toRequestBody("application/json; charset=utf-8".toMediaType()))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                error("Export failed: ${response.code}")
            }
        }
    }

    private fun ensureConfigured() {
        require(baseUrl.isNotBlank()) { "BASE_URL is missing" }
        require(token.isNotBlank()) { "BACKUP_API_TOKEN is missing" }
    }
}
