package com.example.studysage.feature_study_sage_app.domain.model

data class Task(
    val id: Int,
    val taskSubjectId: Int,
    val title: String,
    val description: String,
    val date: Long,
    val priority: Int,
    val relatedTaskToSubject: String,
    val isTaskComplete: Boolean
)
