package com.example.studysage.feature_study_sage_app.data.local.mapper

import com.example.studysage.feature_study_sage_app.data.local.entity.SessionEntity
import com.example.studysage.feature_study_sage_app.domain.model.Session

fun SessionEntity.toSession(): Session {
    return Session(
        id = id,
        studySessionToSubject = sessionSubjectId,
        relatedStudySessionToSubject = relatedSessionToSubject,
        date = date,
        duration = duration
    )
}

fun Session.toSessionEntity(): SessionEntity {
    return SessionEntity(
        id = id,
        sessionSubjectId = studySessionToSubject,
        relatedSessionToSubject = relatedStudySessionToSubject,
        date = date,
        duration = duration
    )
}