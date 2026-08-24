package dohdohs.grocery.dohfreshmarket.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
private val FreshMarketColors = lightColorScheme(
    primary = MarketGreen,
    onPrimary = PureWhite,
    primaryContainer = ChipCream,
    onPrimaryContainer = ForestInk,
    secondary = FreshTeal,
    onSecondary = PureWhite,
    tertiary = HarvestGold,
    onTertiary = ForestInk,
    background = WarmCream,
    onBackground = ForestInk,
    surface = PureWhite,
    onSurface = ForestInk,
    onSurfaceVariant = SageMuted,
    outline = SoftBorder,
)
@Composable
fun ProductAppHUFFNTheme(darkTheme: Boolean = false, dynamicColor: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = FreshMarketColors, typography = AppTypography, content = content)
}
