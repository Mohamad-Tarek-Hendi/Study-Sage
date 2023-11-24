package com.example.studysage.feature_study_sage_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SessionEntity(
    val sessionSubjectId: Int,
    val relatedSessionToSubject: String,
    val date: Long,
    val duration: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)