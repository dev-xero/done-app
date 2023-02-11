package dev.xero.doneapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DefaultColors = lightColors(
	background = background,
	onSurface = onSurface,
	primary = primary,
	secondary = secondary,
)

@Composable
fun DoneAppTheme(content: @Composable () -> Unit) {
	val colors = DefaultColors

	MaterialTheme(
		colors = colors,
		typography = Typography,
		shapes = Shapes,
		content = content
	)
}