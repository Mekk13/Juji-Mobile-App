package com.example.juji.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = DarkGreen,
    onPrimary = PaleGreen,
    background = MossGreen,
    onBackground = DarkGreen,
    surface = PaleGreen,
    onSurface = DarkGreen,
    error = DarkRed
)

@Composable
fun JujiTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
