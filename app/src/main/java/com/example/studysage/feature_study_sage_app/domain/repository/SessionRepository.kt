package com.example.studysage.feature_study_sage_app.domain.repository

import androidx.room.Dao
import com.example.studysage.feature_study_sage_app.domain.model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionRepository {

    suspend fun insertSession(session: Session)

    suspend fun deleteSession(session: Session)

    suspend fun deleteRelatedSessionsBySpecificSubject(subjectId: Int)

    fun getSessionList(): Flow<List<Session>>

    fun getRelatedSessionBySpecificSubject(subjectId: Int): Flow<List<Session>>

    fun getTotalSessionDuration(): Flow<Long>

    fun getTotalRelatedSessionDurationBySpecificSubject(subjectId: Int): Flow<Long>

    fun getAllSubjectList(): Flow<List<Session>>
}