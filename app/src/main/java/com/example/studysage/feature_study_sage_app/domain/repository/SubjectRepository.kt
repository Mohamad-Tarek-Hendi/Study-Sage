package com.example.studysage.feature_study_sage_app.domain.repository

import com.example.studysage.feature_study_sage_app.data.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

    suspend fun upsertSubject(subject: SubjectEntity)

    suspend fun deleteSubject(subjectId: Int)

    fun getTotalSubjectCount(): Flow<Int>

    fun getTotalGoalHour(): Flow<Float>

    suspend fun getSubjectById(subjectId: Int): SubjectEntity?

    fun getAllSubjectList(): Flow<List<SubjectEntity>>
}