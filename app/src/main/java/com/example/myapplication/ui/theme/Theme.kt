package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkPokerColorScheme = darkColorScheme(
    primary = PokerGreen,
    onPrimary = PokerWhite,
    secondary = PokerGold,
    onSecondary = PokerBlack,
    tertiary = PokerAccent,
    onTertiary = PokerWhite,
    background = DarkPokerBackground,
    onBackground = PokerWhite,
    surface = DarkPokerSurface,
    onSurface = PokerWhite,
    surfaceVariant = DarkPokerCard,
    onSurfaceVariant = PokerWhite,
    error = PokerRed,
    onError = PokerWhite
)

private val LightPokerColorScheme = lightColorScheme(
    primary = PokerGreen,
    onPrimary = PokerWhite,
    secondary = PokerGold,
    onSecondary = PokerBlack,
    tertiary = PokerAccent,
    onTertiary = PokerWhite,
    background = PokerWhite,
    onBackground = PokerBlack,
    surface = PokerWhite,
    onSurface = PokerBlack,
    surfaceVariant = PokerGray,
    onSurfaceVariant = PokerWhite,
    error = PokerRed,
    onError = PokerWhite
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Always use dark theme for poker
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic colors for consistent poker theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkPokerColorScheme
        else -> LightPokerColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}