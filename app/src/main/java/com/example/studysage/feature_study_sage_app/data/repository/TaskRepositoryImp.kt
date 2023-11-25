package com.example.studysage.feature_study_sage_app.data.repository

import com.example.studysage.feature_study_sage_app.data.dao.TaskDao
import com.example.studysage.feature_study_sage_app.data.entity.TaskEntity
import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImp @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun upsertTask(task: TaskEntity) {
        taskDao.upsertTask(task = task)
    }

    override suspend fun deleteTaskById(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRelatedTasksBySpecificSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): TaskEntity? {
        TODO("Not yet implemented")
    }

    override fun getRelatedTasksBySpecificSubject(subjectId: Int): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }

    override fun getTaskList(): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }
}