package dev.xero.doneapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

private const val TAG = "AppViewModel"

class AppViewModel: ViewModel() {
	private val _uiState = MutableStateFlow(AppUiState(tasksList = mutableListOf()))
	val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

	fun getCheckedStateOf(id: String): String {
		return _uiState.value.tasksList.find {
			it["id"] == id
		}!!["checked"]!!
	}

	private fun checkTasksLeft(currentState: MutableList<MutableMap<String, String>>): Int {
		return currentState.count {
			it["checked"] == "false"
		}
	}

	fun addTask(task: String) {
		val newID = UUID.randomUUID().toString()

		val newTaskItem = mutableMapOf(
			"id" to newID,
			"task" to task,
			"checked" to "false"
		)

		val newTempState = _uiState.value.tasksList.toMutableList()

		newTempState.add(newTaskItem)

		val tasksLeft = checkTasksLeft(currentState = newTempState)

		// UPDATE THE STATE
		_uiState.update {
			currentState -> currentState.copy(
				tasksList = newTempState,
				tasksLeft = tasksLeft
			)
		}

		// DEBUGGING
		Log.d(TAG, _uiState.value.tasksList.toString())

	}

	fun checkTask(id: String) {
		val newTempState = _uiState.value.tasksList.toMutableList()
		val newCheckedState = if(newTempState.find { it["id"] == id }!!["checked"] == "true") "false" else "true"

		newTempState.find { it["id"] == id }!!["checked"] = newCheckedState

		val tasksLeft = checkTasksLeft(currentState = newTempState)

		// UPDATE THE STATE
		_uiState.update {
			currentState -> currentState.copy(
				tasksList = newTempState,
				tasksLeft = tasksLeft
			)
		}


		// DEBUGGING
		Log.d(TAG, _uiState.value.tasksList.toString())

	}



}