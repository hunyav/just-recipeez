package com.example.justrecipeez.data.local

import androidx.room.TypeConverter

class ListStringConverters {
    @TypeConverter
    fun fromList(value: List<String>): String = value.joinToString("\n")

    @TypeConverter
    fun toList(value: String): List<String> = value
        .split("\n")
        .map { it.trim() }
        .filter { it.isNotBlank() }
}
