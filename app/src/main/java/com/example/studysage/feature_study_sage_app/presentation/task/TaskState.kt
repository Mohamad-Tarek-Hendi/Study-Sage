package com.example.studysage.feature_study_sage_app.presentation.task

import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.presentation.common.util.Priority

data class TaskState(
    val taskId: Int? = null,
    val subjectId: Int? = null,
    val title: String = "",
    val description: String = "",
    val date: Long? = null,
    val priority: Priority = Priority.LOW,
    val relatedTaskToSubject: String? = null,
    val subjectList: List<Subject> = emptyList(),
    val isTaskIsComplete: Boolean = false
)
