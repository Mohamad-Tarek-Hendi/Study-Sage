package com.example.studysage.feature_study_sage_app.domain.model

data class Session(
    val id: Int,
    val studySessionToSubject: Int,
    val relatedStudySessionToSubject: String,
    val date: Long,
    val duration: Long
)