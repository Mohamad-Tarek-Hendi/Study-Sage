package com.example.studysage.feature_study_sage_app.presentation.session

import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Subject

data class SessionState(
    val subjectList: List<Subject> = emptyList(),
    val sessionList: List<Session> = emptyList(),
    val relatedToSubject: String? = null,
    val subjectId: Int? = null,
    val session: Session? = null
)