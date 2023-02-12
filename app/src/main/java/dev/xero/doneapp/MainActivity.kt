package dev.xero.doneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.xero.doneapp.ui.AppViewModel
import dev.xero.doneapp.ui.theme.*

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			DoneAppTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = background
				) {
					DoneApp()
				}
			}
		}
	}
}

/**
 * Done App Composable
 * */
@Composable
private fun DoneApp(
	modifier: Modifier = Modifier,
	appViewModel: AppViewModel = viewModel()
) {
	Scaffold(
		modifier = modifier,
		topBar = { AppBar() }
	) {
		contentPadding -> Column(
			modifier = modifier.padding(contentPadding)
		) {
			val appUiState by appViewModel.uiState.collectAsState()

			StatsBar(tasksLeft = appUiState.tasksLeft)
		}
	}
}

/**
 * App Bar Composable
 * */
@Composable
private fun AppBar(
	modifier: Modifier = Modifier
) {
	Card(
		elevation = 0.dp,
		backgroundColor = accent_3,
		shape = RoundedCornerShape(
			size = 0.dp
		),
		modifier = modifier
	) {
		Row(
			modifier = Modifier
				.padding(16.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Image(
				painter = painterResource(id = R.drawable.icon),
				contentDescription = null,
				modifier = Modifier
					.size(32.dp)
					.weight(1f)
					.wrapContentWidth(
						Alignment.Start
					)
			)

			Text(
				text = stringResource(id = R.string.all_tasks),
				style = MaterialTheme.typography.subtitle1,
				color = primary,
				textAlign = TextAlign.Center,
				modifier = Modifier
					.weight(1f)
			)

			Spacer(
				modifier = Modifier.weight(1f)
			)
		}
	}
}

/**
 * Stats Bar Composable
 * */
@Composable
private fun StatsBar(
	modifier: Modifier = Modifier,
	tasksLeft: Int
) {
	Card( 
		elevation = 0.dp,
		backgroundColor = primary,
		modifier = Modifier.padding(16.dp)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center
		) {
			val tasksNum = if (tasksLeft < 10)
				stringResource(id = R.string.tasks_num, tasksLeft)
			else
				stringResource(id = R.string.tasks_num_single, tasksLeft)
			Text(
				text = tasksNum,
				style = MaterialTheme.typography.h1,
				color = onSurface,
				modifier = Modifier
					.padding(
						start = 8.dp,
						end = 12.dp
					)
			)

			Text(
				text = stringResource(id = R.string.things_left),
				style = MaterialTheme.typography.h3,
				color = secondary,
				modifier = Modifier
					.padding(
						end = 8.dp
					)
			)
		}
	}
}

/**
 * Done App Preview Composable
 * */
@Preview
@Composable
private fun DoneAppPreview() {
	DoneAppTheme {
		DoneApp()
	}
}
