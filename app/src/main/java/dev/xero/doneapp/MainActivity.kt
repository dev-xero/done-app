package dev.xero.doneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.xero.doneapp.model.TaskItem
import dev.xero.doneapp.ui.AppViewModel
import dev.xero.doneapp.ui.theme.*
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.util.*

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
	val focusManager = LocalFocusManager.current
	val interactionSource = MutableInteractionSource()
	val appUiState by appViewModel.uiState.collectAsState()

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

				StatsBar(
					tasksLeft = appUiState.tasksLeft,
					modifier = Modifier
						.clickable(
							interactionSource = interactionSource,
							indication = null,
							onClick = {
								focusManager.clearFocus()
							}
						)
				)

				TasksInputBox(
					focusManager = focusManager,
					viewModel = appViewModel
				)

				if (appUiState.tasksList.isEmpty()) {
					Column(
						horizontalAlignment = Alignment.CenterHorizontally,
						verticalArrangement = Arrangement.Center,
						modifier = Modifier
							.fillMaxWidth()
							.padding(
								vertical = 32.dp
							)
					) {
						Image(
							painter = painterResource(id = R.drawable.no_tasks_icon),
							contentDescription = null,
							modifier = Modifier.size(96.dp)
						)

						Text(
							text = stringResource(id = R.string.no_tasks_left),
							style = MaterialTheme.typography.subtitle1,
							color = accent_2,
							textAlign = TextAlign.Center,
							modifier = Modifier
								.padding(top = 8.dp)
						)
					}
				}
			}

			if (appUiState.tasksList.isNotEmpty()) {
				items(appUiState.tasksList) {
					task -> TaskItemComposable(
						task = task,
						viewModel = appViewModel,
						modifier = Modifier.padding(bottom = 8.dp)
					)
				}
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

			val textDisplay = if (tasksLeft == 1)
				stringResource(id = R.string.things_left_singular)
			else
				stringResource(id = R.string.things_left)

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
				text = textDisplay,
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
	focusManager: FocusManager,
	viewModel: AppViewModel
) {
	var textInput by rememberSaveable {
		mutableStateOf("")
	}

	OutlinedTextField(
		value = textInput,
		onValueChange = { textInput = it },
		label = {
			Text(
				text = stringResource(id = R.string.input_label),
				style = MaterialTheme.typography.body1
			)
		},
		maxLines = 1,
		singleLine = true,
		keyboardActions = KeyboardActions(
			onDone = {
				focusManager.clearFocus()
				addUITask(task = textInput, viewModel = viewModel)
				textInput = ""
			}
		),
		keyboardOptions = KeyboardOptions.Default.copy(
			imeAction = ImeAction.Done
		),
		colors = TextFieldDefaults.outlinedTextFieldColors(
			focusedBorderColor = primary,
			unfocusedBorderColor = accent_2,
			textColor = onSurface,
			focusedLabelColor = primary,
			unfocusedLabelColor = accent_2
		),
		shape = RoundedCornerShape(10.dp),
		modifier = modifier
			.padding(
				top = 8.dp,
				start = 16.dp,
				end = 16.dp,
				bottom = 16.dp
			)
			.fillMaxWidth()
	)
}

/**
 * Function to add a new task to the list
 * */
private fun addUITask(
	task: String,
	viewModel: AppViewModel
) {
	viewModel.addTask(task)
}

/**
 * Task Item Composable
 * */
@Composable
private fun TaskItemComposable(
	modifier: Modifier = Modifier,
	task: TaskItem,
	viewModel: AppViewModel
) {

	Card(
		elevation = 0.dp,
		shape = RoundedCornerShape(10.dp),
		backgroundColor = accent_1,
		modifier = modifier
			.padding(
				horizontal = 16.dp
			)
		)
	{
		val delete = SwipeAction(
			onSwipe = {
				 viewModel.deleteTask(id = task.id)
			},
			icon = {
				Icon(
					painter = painterResource(id = R.drawable.trash_icon),
					contentDescription = null,
					tint = onSurface,
					modifier = Modifier
						.padding(16.dp)
				)
			},
			background = Color(0xFFF33854)
		)

		SwipeableActionsBox(
			endActions = listOf(delete),
			backgroundUntilSwipeThreshold = Color(0xFF0B0F14),
			swipeThreshold = 120.dp
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.background(color = accent_1)
			) {
				var checkState = task.checked

				Checkbox(
					checked = checkState,
					onCheckedChange = {
						viewModel.checkTask(id = task.id)
						checkState = it
					},
					colors = CheckboxDefaults.colors(
						checkedColor = primary,
						uncheckedColor = accent_2,
						checkmarkColor = onSurface
					),
					modifier = Modifier
						.wrapContentWidth()
						.wrapContentHeight()
				)

				Text(
					text = task.task,
					style = MaterialTheme.typography.body1,
					color = if (checkState) accent_2 else onSurface,
					fontSize = 16.sp,
					textDecoration = if (checkState)
						TextDecoration.LineThrough
					else
						TextDecoration.None,
					modifier = Modifier.padding(
						top = 12.dp,
						end = 12.dp,
						bottom = 12.dp
					)
				)
			}
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

@Preview
@Composable
private fun TaskItemPreview() {
	DoneAppTheme {
		TaskItemComposable(task = TaskItem(
			id = UUID.randomUUID(),
			task = "lol hahahahhsdfkasfasfpas fasdfkaspdf asfdjkkdsa dak sdfpas fasfkaspldf asdfkasdfa skdfas asfdal;sjf asf",
			checked = false
		), viewModel = viewModel())
	}
}