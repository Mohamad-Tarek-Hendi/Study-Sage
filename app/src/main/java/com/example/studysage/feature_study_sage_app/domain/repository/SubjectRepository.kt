package com.example.studysage.feature_study_sage_app.domain.repository

import com.example.studysage.feature_study_sage_app.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

    suspend fun upsertSubject(subject: Subject)

    suspend fun deleteSubject(subjectId: Int)

    fun getTotalSubjectCount(): Flow<Int>

    fun getTotalGoalHour(): Flow<Float>

    suspend fun getSubjectById(subjectId: Int): Subject?

    fun getAllSubjectList(): Flow<List<Subject>>
}