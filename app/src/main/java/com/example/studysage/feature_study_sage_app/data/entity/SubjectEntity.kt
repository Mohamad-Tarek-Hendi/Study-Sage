package com.example.studysage.feature_study_sage_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubjectEntity(
    val name: String,
    val goalHours: Float,
    val color: List<Int>,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)