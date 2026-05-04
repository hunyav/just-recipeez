package com.example.justrecipeez.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    background = Parchment,
    surface = Parchment,
    surfaceVariant = ParchmentDark,
    primary = InkBrown,
    onPrimary = Parchment,
    secondary = Sage,
    onSecondary = Parchment,
    tertiary = RoseSalt,
    onTertiary = Parchment,
    outline = NeutralOutline,
    onBackground = InkText,
    onSurface = InkText
)

private val DarkColors = darkColorScheme(
    background = InkText,
    surface = InkText,
    surfaceVariant = InkText,
    primary = Sage,
    onPrimary = Parchment,
    secondary = RoseSalt,
    onSecondary = Parchment,
    tertiary = Spice,
    onTertiary = Parchment,
    outline = NeutralOutline,
    onBackground = Parchment,
    onSurface = Parchment
)

private val AppShapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(6.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(28.dp)
)

@Composable
fun JustRecipeezTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
