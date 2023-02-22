package dev.xero.doneapp.model

import java.util.*

data class TaskItem(
	val id: UUID,
	val task: String,
	var checked: Boolean = false
)
