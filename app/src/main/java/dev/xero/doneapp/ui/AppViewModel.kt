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
	private val _uiState = MutableStateFlow(AppUiState())
	val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

	private fun checkTasksLeft(currentState: MutableList<MutableMap<String, String>>): Int {
		return currentState.count {
			it["checked"] == "false"
		}
	}

	fun getCheckedStateOf(id: String): String {
		Log.d(TAG, id)
		return _uiState.value.tasksList.find {
			it["id"] == id
		}?.get("checked") ?: "false"
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
			it.copy(
				tasksList = newTempState,
				tasksLeft = tasksLeft
			)
		}

		// DEBUGGING
		Log.d(TAG, _uiState.value.tasksList.toString())

	}

	fun checkTask(id: String) {
		val newTempState = _uiState.value.tasksList.toMutableList()
		val task = newTempState.firstOrNull { it["id"] == id }
		val newCheckedState = if (task?.get("checked") == "true") "false" else "true"
		task?.set("checked", newCheckedState)

		val tasksLeft = checkTasksLeft(currentState = newTempState)

		// UPDATE THE STATE
		_uiState.update {
			it.copy(
				tasksList = newTempState,
				tasksLeft = tasksLeft
			)
		}


		// DEBUGGING
		Log.d(TAG, _uiState.value.tasksList.toString())

	}

	fun deleteTask(id: String) {
		val newTempState = _uiState.value.tasksList.filterNot {
			it["id"] == id
		}.toMutableList()

		val tasksLeft = checkTasksLeft(currentState = newTempState)

		// UPDATE THE STATE
		_uiState.update {
			it.copy(
				tasksList = newTempState,
				tasksLeft = tasksLeft
			)
		}

		// DEBUGGING
		Log.d(TAG, _uiState.value.tasksList.toString())
		Log.d(TAG, _uiState.value.tasksLeft.toString())

	}

}