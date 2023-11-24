package com.example.studysage.feature_study_sage_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studysage.feature_study_sage_app.data.dao.SessionDao
import com.example.studysage.feature_study_sage_app.data.dao.SubjectDao
import com.example.studysage.feature_study_sage_app.data.dao.TaskDao
import com.example.studysage.feature_study_sage_app.data.entity.SessionEntity
import com.example.studysage.feature_study_sage_app.data.entity.SubjectEntity
import com.example.studysage.feature_study_sage_app.data.entity.TaskEntity

@Database(
    entities = [
        SubjectEntity::class,
        TaskEntity::class,
        SessionEntity::class
    ],
    version = 1
)
abstract class StudySageDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun taskDao(): TaskDao
    abstract fun sessionDao(): SessionDao
}