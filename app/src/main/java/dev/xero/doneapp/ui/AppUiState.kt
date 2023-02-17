package dev.xero.doneapp.ui

data class AppUiState (
	var tasksList: List<MutableMap<String, String>> = listOf(),
	var tasksLeft: Int = 0
)