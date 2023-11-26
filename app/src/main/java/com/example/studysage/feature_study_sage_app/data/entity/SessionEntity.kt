package com.example.studysage.feature_study_sage_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SessionEntity(
    val sessionSubjectId: Int? = null,
    val relatedSessionToSubject: String? = null,
    val date: Long? = null,
    val duration: Long? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)