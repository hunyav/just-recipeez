package com.example.justrecipeez.data.local

import androidx.room.TypeConverter
import org.json.JSONArray

class ListStringConverters {
    @TypeConverter
    fun fromList(value: List<String>?): String {
        if (value.isNullOrEmpty()) return "[]"
        return JSONArray(value).toString()
    }

    @TypeConverter
    fun toList(value: String?): List<String> {
        if (value.isNullOrBlank()) return emptyList()
        return runCatching {
            val arr = JSONArray(value)
            (0 until arr.length())
                .mapNotNull { idx -> arr.optString(idx).takeIf { it.isNotBlank() } }
        }.getOrElse {
            value.split("\n")
                .map { it.trim() }
                .filter { it.isNotBlank() }
        }
    }
}
