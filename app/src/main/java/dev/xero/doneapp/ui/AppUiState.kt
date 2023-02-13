package dev.xero.doneapp.ui

data class AppUiState (
	var tasks: List<Map<String, String>> = mutableListOf<Map<String, String>>(),
	var tasksLeft: Int = 0
)