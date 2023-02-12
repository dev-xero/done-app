package dev.xero.doneapp.ui

data class AppUiState (
	var tasks: List<String> = mutableListOf<String>(),
	var tasksLeft: Int = 0
)