package com.example.studysage.feature_study_sage_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.studysage.feature_study_sage_app.data.local.entity.SessionEntity
import com.example.studysage.feature_study_sage_app.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert
    suspend fun insertSession(session: SessionEntity)

    @Delete
    suspend fun deleteSession(session: SessionEntity)

    @Query("DELETE FROM SessionEntity WHERE sessionSubjectId=:subjectId")
    suspend fun deleteRelatedSessionsBySpecificSubject(subjectId: Int)

    @Query("SELECT * FROM SessionEntity")
    fun getSessionList(): Flow<List<SessionEntity>>

    @Query("SELECT * FROM SessionEntity WHERE sessionSubjectId=:subjectId")
    fun getRelatedSessionBySpecificSubject(subjectId: Int): Flow<List<SessionEntity>>

    @Query("SELECT SUM(duration) FROM SessionEntity ")
    fun getTotalSessionDuration(): Flow<Long>

    @Query("SELECT SUM(duration) FROM SessionEntity WHERE sessionSubjectId=:subjectId")
    fun getTotalRelatedSessionDurationBySpecificSubject(subjectId: Int): Flow<Long>

    @Query("SELECT * FROM SubjectEntity")
    fun getAllSubjectList(): Flow<List<SubjectEntity>>
}