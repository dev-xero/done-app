package dev.xero.doneapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class AppViewModel: ViewModel() {
	private val _uiState = MutableStateFlow(AppUiState())
	val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
	private val TAG = "AppViewModel"

	fun addTask(task: String) {
		if (task.isNotBlank()) {
			val newTask: Map<String, String> = mapOf(
				"id" to UUID.randomUUID().toString(),
				"task" to task
			)
			val newList = uiState.value.tasks + newTask
			_uiState.update {
				currentState -> currentState.copy(
					tasks = newList,
					tasksLeft = newList.size
				)
			}
		}
		Log.d(TAG, _uiState.value.tasks.toString())
	}

	fun checkTask(id: String) {
		val newList = _uiState.value.tasks.filter {
			it["id"].toString() != id
		}
		_uiState.update {
			currentState -> currentState.copy(
				tasks = newList,
				tasksLeft = newList.size
			)
		}
		Log.d(TAG, _uiState.value.tasks.toString())
	}
}