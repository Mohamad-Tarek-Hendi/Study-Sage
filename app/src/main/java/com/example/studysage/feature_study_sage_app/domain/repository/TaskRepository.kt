package com.example.studysage.feature_study_sage_app.domain.repository

import androidx.room.Dao
import com.example.studysage.feature_study_sage_app.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskRepository {

    suspend fun upsertTask(task: TaskEntity)

    suspend fun deleteTaskById(taskId: Int)

    suspend fun deleteRelatedTasksBySpecificSubject(subjectId: Int)

    suspend fun getTaskById(taskId: Int): TaskEntity?

    fun getRelatedTasksBySpecificSubject(subjectId: Int): Flow<List<TaskEntity>>

    fun getTaskList(): Flow<List<TaskEntity>>
}