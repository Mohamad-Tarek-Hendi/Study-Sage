package com.example.studysage.feature_study_sage_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val taskSubjectId: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val date: Long? = null,
    val priority: Int? = null,
    val relatedTaskToSubject: String? = null,
    val isTaskComplete: Boolean? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
