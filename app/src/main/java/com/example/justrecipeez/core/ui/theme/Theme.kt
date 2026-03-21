package com.example.justrecipeez.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    background = Parchment,
    surface = Parchment,
    surfaceVariant = ParchmentDark,
    primary = InkBrown,
    secondary = InkBrown,
    onBackground = InkText,
    onSurface = InkText,
    onPrimary = Parchment
)

private val DarkColors = darkColorScheme(
    background = InkText,
    surface = InkText,
    primary = Parchment,
    onBackground = Parchment,
    onSurface = Parchment
)

@Composable
fun JustRecipeezTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
