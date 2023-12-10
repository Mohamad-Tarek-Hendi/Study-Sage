package com.example.studysage.feature_study_sage_app.data.local.repository

import com.example.studysage.feature_study_sage_app.data.local.dao.SessionDao
import com.example.studysage.feature_study_sage_app.data.local.mapper.toSession
import com.example.studysage.feature_study_sage_app.data.local.mapper.toSessionEntity
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
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
        sessionDao.deleteSession(
            session = session.toSessionEntity()
        )
    }

    override suspend fun deleteRelatedSessionsBySpecificSubject(subjectId: Int) {
        sessionDao.deleteRelatedSessionsBySpecificSubject(
            subjectId = subjectId
        )
    }

    override fun getSessionList(): Flow<List<Session>> {
        return sessionDao.getSessionList().take(count = 5).map { sessionEntityList ->
            sessionEntityList.map { sessionEntity ->
                sessionEntity.toSession()
            }
        }
    }

    override fun getRelatedSessionBySpecificSubject(subjectId: Int): Flow<List<Session>> {
        return sessionDao.getRelatedSessionBySpecificSubject(subjectId = subjectId).take(count = 10)
            .map { sessionEntityList ->
                sessionEntityList.map { sessionEntity ->
                    sessionEntity.toSession()
                }
            }
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDao.getTotalSessionDuration()
    }

    override fun getTotalRelatedSessionDurationBySpecificSubject(subjectId: Int): Flow<Long> {
        return sessionDao.getTotalRelatedSessionDurationBySpecificSubject(subjectId = subjectId)
    }

    override fun getAllSubjectList(): Flow<List<Session>> {
        return sessionDao.getSessionList().map { sessionEntityList ->
            sessionEntityList.map { sessionEntity ->
                sessionEntity.toSession()
            }
        }
    }
}