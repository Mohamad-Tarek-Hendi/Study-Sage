package com.example.studysage.feature_study_sage_app.domain.repository

import androidx.room.Dao
import com.example.studysage.feature_study_sage_app.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskRepository {

    suspend fun upsertTask(task: Task)

    suspend fun deleteTaskById(taskId: Int)

    suspend fun deleteRelatedTasksBySpecificSubject(subjectId: Int)

    suspend fun getTaskById(taskId: Int): Task?

    fun getRelatedUpcomingTasksBySpecificSubject(subjectId: Int): Flow<List<Task>>

    fun getRelatedCompletedTasksBySpecificSubject(subjectId: Int): Flow<List<Task>>

    fun getTaskList(): Flow<List<Task>>
}