package dev.xero.doneapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import dev.xero.doneapp.model.TaskItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

private const val TAG = "AppViewModel"

class AppViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(AppUiState())
	val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

	private fun getTasksLeft(tasksList: MutableList<TaskItem>): Int {
		var tasksLeft = 0
		tasksList.forEach {
			if (!it.checked) {
				tasksLeft += 1
			}
		}
		return tasksLeft
	}

	fun addTask(taskString: String) {
		val tasksList: MutableList<TaskItem> = _uiState.value.tasksList.toMutableList()
		tasksList.add(TaskItem(
			id = UUID.randomUUID(),
			task = taskString,
			checked = false
		))
		val tasksLeft = getTasksLeft(tasksList)

		_uiState.update {
			it.copy (
				tasksList = tasksList,
				tasksLeft = tasksLeft
			)
		}

		Log.d(TAG, _uiState.value.tasksList.toString())
	}

	fun checkTask(id: UUID) {
		val tasksList: MutableList<TaskItem> = _uiState.value.tasksList.toMutableList()
		tasksList.forEach {
			if (it.id == id) {
				it.checked = !it.checked
			}
		}

		val tasksLeft = getTasksLeft(tasksList)

		_uiState.update {
			it.copy(
				tasksList = tasksList,
				tasksLeft = tasksLeft
			)
		}

		Log.d(TAG, tasksList.toString())
	}

	fun deleteTask(id: UUID) {
		val tasksList: MutableList<TaskItem> = _uiState.value.tasksList.toMutableList()
		tasksList.remove(tasksList.find { it.id == id})

		val tasksLeft = getTasksLeft(tasksList)

		_uiState.update {
			it.copy(
				tasksList = tasksList,
				tasksLeft = tasksLeft
			)
		}

		Log.d(TAG, tasksList.toString())
	}
}