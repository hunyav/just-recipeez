package com.example.justrecipeez.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.justrecipeez.data.repository.RecipeRepository
import com.example.justrecipeez.feature.detail.RecipeDetailScreen
import com.example.justrecipeez.feature.detail.RecipeDetailViewModel
import com.example.justrecipeez.feature.edit.EditRecipeScreen
import com.example.justrecipeez.feature.edit.EditRecipeViewModel
import com.example.justrecipeez.feature.list.RecipeListScreen
import com.example.justrecipeez.feature.list.RecipeListViewModel
import com.example.justrecipeez.feature.settings.SettingsScreen
import com.example.justrecipeez.feature.settings.SettingsViewModel

@Composable
fun RecipeNavGraph(
    recipeRepository: RecipeRepository,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == Destinations.LIST,
                    onClick = {
                        navController.navigate(Destinations.LIST) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "Recipes") },
                    label = { Text("Recipes") }
                )
                NavigationBarItem(
                    selected = currentRoute == Destinations.SETTINGS,
                    onClick = {
                        navController.navigate(Destinations.SETTINGS) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        },
        modifier = modifier
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.LIST,
            modifier = Modifier.padding(padding)
        ) {
            composable(Destinations.LIST) {
                val viewModel: RecipeListViewModel = viewModel(factory = RecipeListViewModel.factory(recipeRepository))
                val state = viewModel.uiState.collectAsStateWithLifecycle().value
                RecipeListScreen(
                    uiState = state,
                    onQueryChange = viewModel::onQueryChange,
                    onRecipeClick = { id -> navController.navigate(Destinations.detailRoute(id)) },
                    onAddClick = { navController.navigate(Destinations.editRoute(null)) },
                    onFavoriteToggle = viewModel::onFavoriteToggle
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
                    onTagsChange = viewModel::onTagsChange,
                    onIngredientsChange = viewModel::onIngredientsChange,
                    onInstructionsChange = viewModel::onInstructionsChange,
                    onNotesChange = viewModel::onNotesChange,
                    onPrepMinutesChange = viewModel::onPrepMinutesChange,
                    onCookMinutesChange = viewModel::onCookMinutesChange,
                    onServingsChange = viewModel::onServingsChange,
                    onImageUriChange = viewModel::onImageUriChange
                )
            }

            composable(Destinations.SETTINGS) {
                val viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.factory(recipeRepository))
                val state = viewModel.uiState.collectAsStateWithLifecycle().value
                SettingsScreen(
                    uiState = state,
                    onImportClick = viewModel::importFromBackup,
                    onExportClick = viewModel::exportToBackup
                )
            }
        }
    }
}
