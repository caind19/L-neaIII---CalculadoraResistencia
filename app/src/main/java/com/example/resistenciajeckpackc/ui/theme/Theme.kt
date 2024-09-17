package com.example.resistenciajeckpackc.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Definir el esquema de colores claro
private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC5),
    onSecondary = Color.Black,
    background = Color(0xFFFFFFFF),
    onBackground = Color.Black,
    surface = Color(0xFFFFFFFF),
    onSurface = Color.Black
)

// Función para aplicar el tema
@Composable
fun TemaResistenciaC(
    content: @Composable () -> Unit
) {
    // Usar solo el esquema de colores claro
    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = Typography, // Usa Typography predeterminado o define uno personalizado
        content = content
    )
}
