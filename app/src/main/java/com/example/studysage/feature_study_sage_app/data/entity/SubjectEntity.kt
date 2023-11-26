package com.example.studysage.feature_study_sage_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubjectEntity(
    val name: String? = null,
    val goalHours: Float? = null,
    val color: List<Int>? = emptyList(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)