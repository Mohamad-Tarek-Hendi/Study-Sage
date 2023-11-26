package com.example.studysage.feature_study_sage_app.domain.model

data class Session(
    val id: Int? = null,
    val studySessionToSubject: Int? = null,
    val relatedStudySessionToSubject: String? = null,
    val date: Long? = null,
    val duration: Long? = null
)