package com.example.studysage.feature_study_sage_app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.studysage.feature_study_sage_app.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Query("DELETE FROM TaskEntity WHERE id=:taskId")
    suspend fun deleteTaskById(taskId: Int)

    @Query("DELETE FROM TaskEntity WHERE taskSubjectId=:subjectId")
    suspend fun deleteRelatedTasksBySpecificSubject(subjectId: Int)

    @Query("SELECT * FROM TaskEntity WHERE id=:taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Query("SELECT * FROM TaskEntity WHERE taskSubjectId=:subjectId  ORDER BY date DESC , priority DESC")
    fun getRelatedTasksBySpecificSubject(subjectId: Int): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity ORDER BY date DESC , priority DESC")
    fun getTaskList(): Flow<List<TaskEntity>>
}