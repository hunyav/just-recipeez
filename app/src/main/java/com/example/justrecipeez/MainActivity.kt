package com.example.justrecipeez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.justrecipeez.core.ui.theme.JustRecipeezTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as RecipeApplication).container
        setContent {
            JustRecipeezTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    RecipeApp(container = appContainer)
                }
            }
        }
    }
}
