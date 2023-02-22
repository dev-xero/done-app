package dev.xero.doneapp.ui

import dev.xero.doneapp.model.TaskItem

data class AppUiState (
	var tasksList: List<TaskItem> = emptyList(),
	var tasksLeft: Int = 0
)