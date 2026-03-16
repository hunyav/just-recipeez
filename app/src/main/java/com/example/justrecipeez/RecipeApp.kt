package com.example.justrecipeez

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.justrecipeez.navigation.RecipeNavGraph

@Composable
fun RecipeApp(
    container: AppContainer,
    modifier: Modifier = Modifier
) {
    RecipeNavGraph(
        recipeRepository = container.recipeRepository,
        modifier = modifier
    )
}
