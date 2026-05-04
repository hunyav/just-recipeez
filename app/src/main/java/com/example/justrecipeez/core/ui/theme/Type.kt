package com.example.justrecipeez.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DisplaySerif = FontFamily.Serif
private val BodySans = FontFamily.SansSerif

val Typography = Typography(
    displaySmall = TextStyle(fontFamily = DisplaySerif, fontWeight = FontWeight.Bold, fontSize = 36.sp, lineHeight = 40.sp),
    headlineSmall = TextStyle(fontFamily = DisplaySerif, fontWeight = FontWeight.SemiBold, fontSize = 24.sp),
    titleLarge = TextStyle(fontFamily = DisplaySerif, fontWeight = FontWeight.SemiBold, fontSize = 22.sp),
    titleMedium = TextStyle(fontFamily = DisplaySerif, fontWeight = FontWeight.Medium, fontSize = 18.sp),
    titleSmall = TextStyle(fontFamily = DisplaySerif, fontWeight = FontWeight.Medium, fontSize = 16.sp),

    bodyLarge = TextStyle(fontFamily = BodySans, fontSize = 16.sp, lineHeight = 22.sp),
    bodyMedium = TextStyle(fontFamily = BodySans, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontFamily = BodySans, fontSize = 12.sp, lineHeight = 16.sp),

    labelLarge = TextStyle(fontFamily = BodySans, fontWeight = FontWeight.Medium, fontSize = 14.sp),
    labelMedium = TextStyle(fontFamily = BodySans, fontWeight = FontWeight.Medium, fontSize = 12.sp),
    labelSmall = TextStyle(fontFamily = BodySans, fontWeight = FontWeight.Medium, fontSize = 11.sp)
)
