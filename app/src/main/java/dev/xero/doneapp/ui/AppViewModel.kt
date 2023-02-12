package dev.xero.doneapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel: ViewModel() {
	private val _uiState = MutableStateFlow(AppUiState())
	val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

	fun addTask(task: String) {
		if (task.isNotBlank()) {
			val newTasks = _uiState.value.tasks + listOf<String>(task)
			_uiState.update {
				currentState -> currentState.copy(
					tasks = newTasks
				)
			}
		}
	}
}