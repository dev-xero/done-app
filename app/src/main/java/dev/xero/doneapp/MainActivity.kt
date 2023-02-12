package dev.xero.doneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
		contentPadding -> LazyColumn(
			modifier = modifier
				.padding(contentPadding)
				.fillMaxSize()
		) {
			item {
				val focusManager = LocalFocusManager.current
				val appUiState by appViewModel.uiState.collectAsState()

				StatsBar(
					tasksLeft = appUiState.tasksLeft,
					modifier = Modifier
						.clickable {
							focusManager.clearFocus()
						}
				)

				TasksInputBox(
					focusManager = focusManager
				)
			}
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
		modifier = modifier.padding(16.dp)
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
 * Tasks Input Box Composable
 * */
@Composable
private fun TasksInputBox(
	modifier: Modifier = Modifier,
	focusManager: FocusManager
) {
	var textInput by remember {
		mutableStateOf("")
	}

	OutlinedTextField(
		value = textInput,
		onValueChange = { textInput = it },
		label = {
			Text(
				text = stringResource(id = R.string.input_label),
				color = accent_2,
				style = MaterialTheme.typography.body1
			)
		},
		maxLines = 1,
		singleLine = true,
		keyboardActions = KeyboardActions(
			onDone = { focusManager.clearFocus() }
		),
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = ImeAction.Done
		),
		colors = TextFieldDefaults.outlinedTextFieldColors(
			focusedBorderColor = primary,
			unfocusedBorderColor = accent_2,
			textColor = onSurface
		),
		shape = RoundedCornerShape(10.dp),
		modifier = modifier
			.padding(16.dp)
			.fillMaxWidth()
	)
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
