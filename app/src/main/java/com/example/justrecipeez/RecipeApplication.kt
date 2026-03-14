package com.example.justrecipeez

import android.app.Application
import com.example.justrecipeez.data.local.AppDatabase
import com.example.justrecipeez.data.repository.DefaultRecipeRepository

class RecipeApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(
            recipeRepository = DefaultRecipeRepository(
                dao = AppDatabase.getInstance(this).recipeDao()
            )
        )
    }
}

data class AppContainer(
    val recipeRepository: DefaultRecipeRepository
)
