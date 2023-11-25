package com.example.studysage.feature_study_sage_app.domain.repository

import androidx.room.Dao
import com.example.studysage.feature_study_sage_app.data.entity.SessionEntity
import com.example.studysage.feature_study_sage_app.data.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionRepository {

    suspend fun insertSession(session: SessionEntity)

    suspend fun deleteSession(session: SessionEntity)

    suspend fun deleteRelatedSessionsBySpecificSubject(subjectId: Int)

    fun getSessionList(): Flow<List<SessionEntity>>

    fun getRelatedSessionBySpecificSubject(subjectId: Int): Flow<List<SessionEntity>>

    fun getTotalSessionDuration(): Flow<Long>

    fun getTotalRelatedSessionDurationBySpecificSubject(subjectId: Int): Flow<Long>

    fun getAllSubjectList(): Flow<List<SubjectEntity>>
}