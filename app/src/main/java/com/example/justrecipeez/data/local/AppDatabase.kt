package com.example.justrecipeez.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class], version = 2, exportSchema = false)
@TypeConverters(ListStringConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "just_recipeez.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }
}
