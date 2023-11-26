package com.example.studysage.feature_study_sage_app.data.repository

import com.example.studysage.feature_study_sage_app.data.dao.SessionDao
import com.example.studysage.feature_study_sage_app.data.entity.SessionEntity
import com.example.studysage.feature_study_sage_app.data.entity.SubjectEntity
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImp @Inject constructor(
    private val sessionDao: SessionDao
) : SessionRepository {
    override suspend fun insertSession(session: SessionEntity) {
        sessionDao.insertSession(session = session)
    }

    override suspend fun deleteSession(session: SessionEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRelatedSessionsBySpecificSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override fun getSessionList(): Flow<List<SessionEntity>> {
        TODO("Not yet implemented")
    }

    override fun getRelatedSessionBySpecificSubject(subjectId: Int): Flow<List<SessionEntity>> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDao.getTotalSessionDuration()
    }

    override fun getTotalRelatedSessionDurationBySpecificSubject(subjectId: Int): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun getAllSubjectList(): Flow<List<SubjectEntity>> {
        TODO("Not yet implemented")
    }
}