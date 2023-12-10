package com.example.studysage.feature_study_sage_app.data.local.repository

import com.example.studysage.feature_study_sage_app.data.local.dao.SessionDao
import com.example.studysage.feature_study_sage_app.data.local.dao.SubjectDao
import com.example.studysage.feature_study_sage_app.data.local.dao.TaskDao
import com.example.studysage.feature_study_sage_app.data.local.mapper.toSubject
import com.example.studysage.feature_study_sage_app.data.local.mapper.toSubjectEntity
import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubjectRepositoryImp @Inject constructor(
    private val subjectDao: SubjectDao,
    private val taskDao: TaskDao,
    private val sessionDao: SessionDao
) : SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {
        subjectDao.upsertSubject(
            subject = subject.toSubjectEntity()
        )
    }

    override suspend fun deleteSubject(subjectId: Int) {
        taskDao.deleteRelatedTasksBySpecificSubject(subjectId = subjectId)
        sessionDao.deleteRelatedSessionsBySpecificSubject(subjectId = subjectId)
        subjectDao.deleteSubject(subjectId = subjectId)
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        return subjectDao.getTotalSubjectCount()
    }

    override fun getTotalGoalHour(): Flow<Float> {
        return subjectDao.getTotalGoalHour()
    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        return subjectDao.getSubjectById(subjectId = subjectId)?.toSubject()
    }

    override fun getAllSubjectList(): Flow<List<Subject>> {
        return subjectDao.getAllSubjectList().map { subjectEntityList ->
            subjectEntityList.map { subjectEntity ->
                subjectEntity.toSubject()
            }
        }
    }
}