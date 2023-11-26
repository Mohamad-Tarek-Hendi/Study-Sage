package com.example.studysage.feature_study_sage_app.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.studysage.feature_study_sage_app.data.entity.SubjectEntity
import com.example.studysage.feature_study_sage_app.domain.model.Subject

fun SubjectEntity.toSubject(): Subject {
    return Subject(
        id = id,
        name = name,
        goalHours = goalHours,
        color = color?.map {
            Color(it)
        }
    )
}

fun Subject.toSubjectEntity(): SubjectEntity {
    return SubjectEntity(
        name = name,
        goalHours = goalHours,
        color = color?.map {
            it.toArgb()
        }
    )
}