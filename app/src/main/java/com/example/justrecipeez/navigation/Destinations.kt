package com.example.justrecipeez.navigation

object Destinations {
    const val LIST = "list"
    const val DETAIL = "detail"
    const val EDIT = "edit"

    const val RECIPE_ID_ARG = "recipeId"

    const val DETAIL_ROUTE = "$DETAIL/{$RECIPE_ID_ARG}"
    const val EDIT_ROUTE = "$EDIT?$RECIPE_ID_ARG={$RECIPE_ID_ARG}"

    fun detailRoute(recipeId: Long): String = "$DETAIL/$recipeId"
    fun editRoute(recipeId: Long?): String = if (recipeId == null) EDIT else "$EDIT?$RECIPE_ID_ARG=$recipeId"
}
