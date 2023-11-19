package ui

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

object PaintDotKTheme {
    val colors: Colors = Colors()

    class Colors(
        val backgroundMedium: Color = Color(0xFF2B2B2B),

        val material: androidx.compose.material.Colors = darkColors(
            background = Color.Black,
            surface = Color(0xFF2B2B2B),
            primary = Color(0xFF3949AB),
            onSurface = Color.White
        ),
    )
}