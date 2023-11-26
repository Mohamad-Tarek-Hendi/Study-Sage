package com.example.studysage.feature_study_sage_app.domain.model

data class Task(
    val id: Int? = null,
    val taskSubjectId: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val date: Long? = null,
    val priority: Int? = null,
    val relatedTaskToSubject: String? = null,
    val isTaskComplete: Boolean? = null
)
