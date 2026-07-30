package dev.enseor.ifccalendar.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = KodakYellow,
    secondary = KodakRed,
    tertiary = WarmWhite,
    background = DarkGray,
    surface = DarkGray,
    onPrimary = KodakBlack,
    onSecondary = KodakBlack,
    onBackground = WarmWhite,
    onSurface = WarmWhite
)

private val LightColorScheme = lightColorScheme(
    primary = KodakYellow,
    secondary = KodakRed,
    tertiary = KodakBlack,
    background = WarmWhite,
    surface = WarmWhite,
    onPrimary = KodakBlack,
    onSecondary = WarmWhite,
    onBackground = KodakBlack,
    onSurface = KodakBlack
)

@Composable
fun IFCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
