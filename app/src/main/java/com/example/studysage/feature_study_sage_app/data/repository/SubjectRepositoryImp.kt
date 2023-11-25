package com.example.studysage.feature_study_sage_app.data.repository

import com.example.studysage.feature_study_sage_app.data.dao.SubjectDao
import com.example.studysage.feature_study_sage_app.data.entity.SubjectEntity
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImp @Inject constructor(
    private val subjectDao: SubjectDao
) : SubjectRepository {
    override suspend fun upsertSubject(subject: SubjectEntity) {
        subjectDao.upsertSubject(subject = subject)
    }

    override suspend fun deleteSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun getTotalGoalHour(): Flow<Float> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubjectById(subjectId: Int): SubjectEntity? {
        TODO("Not yet implemented")
    }

    override fun getAllSubjectList(): Flow<List<SubjectEntity>> {
        TODO("Not yet implemented")
    }
}