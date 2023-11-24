package com.example.studysage.feature_study_sage_app.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.studysage.feature_study_sage_app.data.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Upsert
    suspend fun upsertSubject(subject: SubjectEntity)

    @Query("DELETE FROM SubjectEntity WHERE id =:subjectId")
    suspend fun deleteSubject(subjectId: Int)

    @Query("SELECT COUNT(*) FROM SubjectEntity")
    fun getTotalSubjectCount(): Flow<Int>

    @Query("SELECT SUM(goalHours) FROM SubjectEntity")
    fun getTotalGoalHour(): Flow<Float>

    @Query("SELECT * FROM SubjectEntity WHERE id =:subjectId")
    suspend fun getSubjectById(subjectId: Int): SubjectEntity?

    @Query("SELECT * FROM SubjectEntity")
    fun getAllSubjectList(): Flow<List<SubjectEntity>>
}