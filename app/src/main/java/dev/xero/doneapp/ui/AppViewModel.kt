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

class AppViewModel: ViewModel() {
	private val _uiState = MutableStateFlow(AppUiState())
	val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

	fun addTask(taskString: String) {
		val tasksList: MutableList<TaskItem> = _uiState.value.tasksList.toMutableList()
		tasksList.add(TaskItem(
			id = UUID.randomUUID(),
			task = taskString,
			checked = false
		))
		_uiState.update {
			it.copy (
				tasksList = tasksList
			)
		}
	}
}