package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val ExecutiveDarkColorScheme = darkColorScheme(
    primary = ChampagneGold,
    onPrimary = DeepBronzeInk,
    primaryContainer = AmberContainer,
    onPrimaryContainer = AmberContainerText,
    secondary = TealAccent,
    onSecondary = Color(0xFF062523),
    secondaryContainer = TealContainer,
    onSecondaryContainer = TealContainerText,
    tertiary = ChampagneGoldMuted,
    onTertiary = DeepBronzeInk,
    background = ObsidianBg,
    onBackground = IvoryText,
    surface = ObsidianSurface,
    onSurface = IvoryText,
    surfaceVariant = ObsidianSurfaceHigh,
    onSurfaceVariant = SlateMutedText,
    surfaceContainerLowest = ObsidianBg,
    surfaceContainerLow = ObsidianSurface,
    surfaceContainer = ObsidianSurfaceHigh,
    surfaceContainerHigh = ObsidianSurfaceHighest,
    outline = SubtleOutlineDark,
    outlineVariant = Color(0xFF242936),
    error = ErrorCoral,
    onError = Color.White,
    errorContainer = ErrorCoralContainer,
    onErrorContainer = Color(0xFFFFDAD9)
)

private val ExecutiveLightColorScheme = lightColorScheme(
    primary = BronzePrimary,
    onPrimary = Color.White,
    primaryContainer = BronzeContainer,
    onPrimaryContainer = BronzeContainerText,
    secondary = DeepTeal,
    onSecondary = Color.White,
    secondaryContainer = DeepTealContainer,
    onSecondaryContainer = DeepTealContainerText,
    tertiary = ChampagneGoldMuted,
    onTertiary = Color.White,
    background = IvoryBg,
    onBackground = CharcoalInk,
    surface = IvorySurface,
    onSurface = CharcoalInk,
    surfaceVariant = IvorySurfaceLow,
    onSurfaceVariant = WarmGrayInk,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = IvorySurfaceLow,
    surfaceContainer = IvorySurfaceHigh,
    surfaceContainerHigh = Color(0xFFE0D7C6),
    outline = SubtleOutlineLight,
    outlineVariant = Color(0xFFE8E1D4),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun RakeshMishraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) ExecutiveDarkColorScheme else ExecutiveLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
