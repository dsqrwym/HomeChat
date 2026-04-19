package org.dsqrwym.homechat.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val lightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    inversePrimary = InversePrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    surfaceTint = SurfaceTintLight,
    inverseSurface = InverseSurfaceLight,
    inverseOnSurface = InverseOnSurfaceLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    scrim = ScrimLight,
    surfaceBright = SurfaceBrightLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceDim = SurfaceDimLight,
)

private val darkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    inversePrimary = InversePrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    surfaceTint = SurfaceTintDark,
    inverseSurface = InverseSurfaceDark,
    inverseOnSurface = InverseOnSurfaceDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    scrim = ScrimDark,
    surfaceBright = SurfaceBrightDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceContainerHighDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
    surfaceContainerLow = SurfaceContainerLowDark,
    surfaceContainerLowest = SurfaceContainerLowestDark,
    surfaceDim = SurfaceDimDark,
)

data class MyAppColors(
    val iconNotification: Color,
    val iconNavSelected: Color,
    val iconNavUnselected: Color,
    val correct: Color,
    val onCorrect: Color,
    val correctContainer: Color,
    val onCorrectContainer: Color,
    val shadowDark: Color,
    val controlActivatedDark: Color,
    val controlNormalDark: Color,
    val controlHighlightDark: Color,
    val textPrimaryInverseDark: Color,
    val textSecondaryAndTertiaryInverseDark: Color,
    val textPrimaryInverseDisableOnlyDark: Color,
    val textSecondaryAndTertiaryInverseDisabledDark: Color,
    val textHintInverseDark: Color,
    val primaryFixed: Color,
    val primaryFixedDim: Color,
    val onPrimaryFixed: Color,
    val onPrimaryFixedVariant: Color,
    val secondaryFixed: Color,
    val secondaryFixedDim: Color,
    val onSecondaryFixed: Color,
    val onSecondaryFixedVariant: Color,
    val tertiaryFixed: Color,
    val tertiaryFixedDim: Color,
    val onTertiaryFixed: Color,
    val onTertiaryFixedVariant: Color,
    val primaryPaletteKeyColor: Color,
    val secondaryPaletteKeyColor: Color,
    val tertiaryPaletteKeyColor: Color,
    val neutralPaletteKeyColor: Color,
    val neutralVariantPaletteKeyColor: Color,
    val errorPaletteKeyColor: Color,
)

val LightIconNotification = Color(0xFFECE2E1)
val LightIconNavUnselected = Color(0xFFB3C9F2)
val LightIconNavSelected = Color(0xFFFFFFFF)
val LightCorrect = Color(0xFF4CAF50)
val LightOnCorrect = Color.White
val LightCorrectContainer = Color(0xFFE6F4EA)
val LightOnCorrectContainer = Color(0xFF1B5E20)

val DarkIconNotification = Color(0xFFECE2E1)
val DarkIconNavUnselected = Color(0xFFABADB3)
val DarkIconNavSelected = Color(0xFFFFFFFF)
val DarkCorrect = Color(0xFF81C784)
val DarkOnCorrect = Color.Black
val DarkCorrectContainer = Color(0xFF1B5E20)
val DarkOnCorrectContainer = Color(0xFFC8E6C9)

val LightExtraColorScheme = MyAppColors(
    iconNotification = LightIconNotification,
    iconNavSelected = LightIconNavSelected,
    iconNavUnselected = LightIconNavUnselected,
    correct = LightCorrect,
    onCorrect = LightOnCorrect,
    correctContainer = LightCorrectContainer,
    onCorrectContainer = LightOnCorrectContainer,
    shadowDark = ShadowLight,
    controlActivatedDark = ControlActivatedLight,
    controlNormalDark = ControlNormalLight,
    controlHighlightDark = ControlHighlightLight,
    textPrimaryInverseDark = TextPrimaryInverseLight,
    textSecondaryAndTertiaryInverseDark = TextSecondaryAndTertiaryInverseLight,
    textPrimaryInverseDisableOnlyDark = TextPrimaryInverseDisableOnlyLight,
    textSecondaryAndTertiaryInverseDisabledDark = TextSecondaryAndTertiaryInverseDisabledLight,
    textHintInverseDark = TextHintInverseLight,
    primaryFixed = PrimaryFixed,
    primaryFixedDim = PrimaryFixedDim,
    onPrimaryFixed = OnPrimaryFixed,
    onPrimaryFixedVariant = OnPrimaryFixedVariant,
    secondaryFixed = SecondaryFixed,
    secondaryFixedDim = SecondaryFixedDim,
    onSecondaryFixed = OnSecondaryFixed,
    onSecondaryFixedVariant = OnSecondaryFixedVariant,
    tertiaryFixed = TertiaryFixed,
    tertiaryFixedDim = TertiaryFixedDim,
    onTertiaryFixed = OnTertiaryFixed,
    onTertiaryFixedVariant = OnTertiaryFixedVariant,
    primaryPaletteKeyColor = PrimaryPaletteKeyColor,
    secondaryPaletteKeyColor = SecondaryPaletteKeyColor,
    tertiaryPaletteKeyColor = TertiaryPaletteKeyColor,
    neutralPaletteKeyColor = NeutralPaletteKeyColor,
    neutralVariantPaletteKeyColor = NeutralVariantPaletteKeyColor,
    errorPaletteKeyColor = ErrorPaletteKeyColor,
)

val DarkExtraColorScheme = MyAppColors(
    iconNotification = DarkIconNotification,
    iconNavSelected = DarkIconNavSelected,
    iconNavUnselected = DarkIconNavUnselected,
    correct = DarkCorrect,
    onCorrect = DarkOnCorrect,
    correctContainer = DarkCorrectContainer,
    onCorrectContainer = DarkOnCorrectContainer,
    shadowDark = ShadowDark,
    controlActivatedDark = ControlActivatedDark,
    controlNormalDark = ControlNormalDark,
    controlHighlightDark = ControlHighlightDark,
    textPrimaryInverseDark = TextPrimaryInverseDark,
    textSecondaryAndTertiaryInverseDark = TextSecondaryAndTertiaryInverseDark,
    textPrimaryInverseDisableOnlyDark = TextPrimaryInverseDisableOnlyDark,
    textSecondaryAndTertiaryInverseDisabledDark = TextSecondaryAndTertiaryInverseDisabledDark,
    textHintInverseDark = TextHintInverseDark,
    primaryFixed = PrimaryFixed,
    primaryFixedDim = PrimaryFixedDim,
    onPrimaryFixed = OnPrimaryFixed,
    onPrimaryFixedVariant = OnPrimaryFixedVariant,
    secondaryFixed = SecondaryFixed,
    secondaryFixedDim = SecondaryFixedDim,
    onSecondaryFixed = OnSecondaryFixed,
    onSecondaryFixedVariant = OnSecondaryFixedVariant,
    tertiaryFixed = TertiaryFixed,
    tertiaryFixedDim = TertiaryFixedDim,
    onTertiaryFixed = OnTertiaryFixed,
    onTertiaryFixedVariant = OnTertiaryFixedVariant,
    primaryPaletteKeyColor = PrimaryPaletteKeyColor,
    secondaryPaletteKeyColor = SecondaryPaletteKeyColor,
    tertiaryPaletteKeyColor = TertiaryPaletteKeyColor,
    neutralPaletteKeyColor = NeutralPaletteKeyColor,
    neutralVariantPaletteKeyColor = NeutralVariantPaletteKeyColor,
    errorPaletteKeyColor = ErrorPaletteKeyColor,
)

val AppExtraColors = staticCompositionLocalOf {
    LightExtraColorScheme
}

@Composable
fun MyMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = MaterialTheme.typography,
    shapes: Shapes = MaterialTheme.shapes,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme
    val extraColors = if (darkTheme) DarkExtraColorScheme else LightExtraColorScheme

    CompositionLocalProvider(AppExtraColors provides extraColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
            typography = typography,
            content = content,
        )
    }
}