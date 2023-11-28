package com.example.studysage.feature_study_sage_app.data.repository

import com.example.studysage.feature_study_sage_app.data.dao.TaskDao
import com.example.studysage.feature_study_sage_app.data.mapper.toTask
import com.example.studysage.feature_study_sage_app.data.mapper.toTaskEntity
import com.example.studysage.feature_study_sage_app.domain.model.Task
import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImp @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(
            task = task.toTaskEntity()
        )
    }

    override suspend fun deleteTaskById(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRelatedTasksBySpecificSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        TODO("Not yet implemented")
    }

    override fun getRelatedUpcomingTasksBySpecificSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getRelatedTasksBySpecificSubject(subjectId = subjectId)
            .map { upcomingTaskEntityList ->
                upcomingTaskEntityList.filter {
                    it.isTaskComplete?.not()!!
                }.map { upcomingTaskEntity ->
                    upcomingTaskEntity.toTask()
                }
            }
    }

    override fun getRelatedCompletedTasksBySpecificSubject(subjectId: Int): Flow<List<Task>> {
        return taskDao.getRelatedTasksBySpecificSubject(subjectId = subjectId)
            .map { completedTaskEntityList ->
                completedTaskEntityList.filter {
                    it.isTaskComplete!!
                }.map { completedTaskEntity ->
                    completedTaskEntity.toTask()
                }
            }
    }

    override fun getTaskList(): Flow<List<Task>> {
        return taskDao.getTaskList().map { taskEntityList ->
            taskEntityList
                .filter {
                    it.isTaskComplete?.not() ?: false
                }.map { taskEntity ->
                    taskEntity.toTask()
                }
        }
    }
}