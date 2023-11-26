package com.example.studysage.feature_study_sage_app.data.repository

import com.example.studysage.feature_study_sage_app.data.dao.SessionDao
import com.example.studysage.feature_study_sage_app.data.mapper.toSessionEntity
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImp @Inject constructor(
    private val sessionDao: SessionDao
) : SessionRepository {
    override suspend fun insertSession(session: Session) {
        sessionDao.insertSession(
            session = session.toSessionEntity()
        )
    }

    override suspend fun deleteSession(session: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRelatedSessionsBySpecificSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override fun getSessionList(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getRelatedSessionBySpecificSubject(subjectId: Int): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDao.getTotalSessionDuration()
    }

    override fun getTotalRelatedSessionDurationBySpecificSubject(subjectId: Int): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun getAllSubjectList(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }
}