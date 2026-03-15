package com.example.justrecipeez.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.justrecipeez.data.repository.RecipeRepository
import com.example.justrecipeez.feature.detail.RecipeDetailScreen
import com.example.justrecipeez.feature.detail.RecipeDetailViewModel
import com.example.justrecipeez.feature.edit.EditRecipeScreen
import com.example.justrecipeez.feature.edit.EditRecipeViewModel
import com.example.justrecipeez.feature.list.RecipeListScreen
import com.example.justrecipeez.feature.list.RecipeListViewModel

@Composable
fun RecipeNavGraph(
    recipeRepository: RecipeRepository,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.LIST,
        modifier = modifier
    ) {
        composable(Destinations.LIST) {
            val viewModel: RecipeListViewModel = viewModel(factory = RecipeListViewModel.factory(recipeRepository))
            val state = viewModel.uiState.collectAsStateWithLifecycle().value
            RecipeListScreen(
                uiState = state,
                onQueryChange = viewModel::onQueryChange,
                onRecipeClick = { id -> navController.navigate(Destinations.detailRoute(id)) },
                onAddClick = { navController.navigate(Destinations.editRoute(null)) },
                onFavoriteToggle = viewModel::onFavoriteToggle,
                onImportClick = viewModel::importFromBackup,
                onExportClick = viewModel::exportToBackup
            )
        }

        composable(
            route = Destinations.DETAIL_ROUTE,
            arguments = listOf(navArgument(Destinations.RECIPE_ID_ARG) { type = NavType.LongType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong(Destinations.RECIPE_ID_ARG) ?: return@composable
            val viewModel: RecipeDetailViewModel = viewModel(
                factory = RecipeDetailViewModel.factory(recipeId, recipeRepository)
            )
            val state = viewModel.uiState.collectAsStateWithLifecycle().value
            RecipeDetailScreen(
                uiState = state,
                onBackClick = { navController.popBackStack() },
                onEditClick = { id -> navController.navigate(Destinations.editRoute(id)) },
                onDeleteClick = { viewModel.onDelete { navController.popBackStack() } },
                onFavoriteToggle = viewModel::onFavoriteToggle
            )
        }

        composable(
            route = Destinations.EDIT_ROUTE,
            arguments = listOf(navArgument(Destinations.RECIPE_ID_ARG) {
                type = NavType.LongType
                defaultValue = -1L
            })
        ) { backStackEntry ->
            val rawId = backStackEntry.arguments?.getLong(Destinations.RECIPE_ID_ARG) ?: -1L
            val recipeId = rawId.takeIf { it > 0 }
            val viewModel: EditRecipeViewModel = viewModel(
                factory = EditRecipeViewModel.factory(recipeId, recipeRepository)
            )
            val state = viewModel.uiState.collectAsStateWithLifecycle().value
            EditRecipeScreen(
                uiState = state,
                onBackClick = { navController.popBackStack() },
                onSaveClick = {
                    viewModel.save { id ->
                        navController.popBackStack()
                        navController.navigate(Destinations.detailRoute(id))
                    }
                },
                onTitleChange = viewModel::onTitleChange,
                onDescriptionChange = viewModel::onDescriptionChange,
                onIngredientsChange = viewModel::onIngredientsChange,
                onInstructionsChange = viewModel::onInstructionsChange,
                onNotesChange = viewModel::onNotesChange,
                onPrepMinutesChange = viewModel::onPrepMinutesChange,
                onCookMinutesChange = viewModel::onCookMinutesChange,
                onServingsChange = viewModel::onServingsChange,
                onImageUriChange = viewModel::onImageUriChange
            )
        }
    }
}
