package com.example.studysage.feature_study_sage_app.data.repository

import com.example.studysage.feature_study_sage_app.data.dao.SubjectDao
import com.example.studysage.feature_study_sage_app.data.mapper.toSubject
import com.example.studysage.feature_study_sage_app.data.mapper.toSubjectEntity
import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubjectRepositoryImp @Inject constructor(
    private val subjectDao: SubjectDao
) : SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {
        subjectDao.upsertSubject(
            subject = subject.toSubjectEntity()
        )
    }

    override suspend fun deleteSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        return subjectDao.getTotalSubjectCount()
    }

    override fun getTotalGoalHour(): Flow<Float> {
        return subjectDao.getTotalGoalHour()
    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        TODO("Not yet implemented")
    }

    override fun getAllSubjectList(): Flow<List<Subject>> {
        return subjectDao.getAllSubjectList().map { subjectEntityList ->
            subjectEntityList.map { subjectEntity ->
                subjectEntity.toSubject()
            }
        }
    }
}