package dev.xero.doneapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.xero.doneapp.R

val OutFit = FontFamily(
	Font(
		resId = R.font.outfit_regular,
		weight = FontWeight.Normal
	),

	Font(
		resId = R.font.outfit_semibold,
		weight = FontWeight.SemiBold
	),

	Font(
		resId = R.font.outfit_black,
		weight = FontWeight.Bold
	)
)

val Typography = Typography(
	h1 = TextStyle(
		fontFamily = OutFit,
		fontWeight = FontWeight.Bold,
		fontSize = 84.sp
	),

	h3 = TextStyle(
		fontFamily = OutFit,
		fontWeight = FontWeight.SemiBold,
		fontSize = 20.sp
	),

	body1 = TextStyle(
		fontFamily = OutFit,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp
	),

	subtitle1 = TextStyle(
		fontFamily = OutFit,
		fontWeight = FontWeight.Bold,
		fontSize = 24.sp
	)
)