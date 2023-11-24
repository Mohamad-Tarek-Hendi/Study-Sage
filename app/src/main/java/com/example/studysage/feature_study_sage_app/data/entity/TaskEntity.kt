package com.example.studysage.feature_study_sage_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val taskSubjectId: Int,
    val title: String,
    val description: String,
    val date: Long,
    val priority: Int,
    val relatedTaskToSubject: String,
    val isTaskComplete: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
