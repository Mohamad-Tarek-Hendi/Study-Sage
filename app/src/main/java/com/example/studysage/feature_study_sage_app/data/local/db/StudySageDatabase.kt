package com.example.studysage.feature_study_sage_app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.studysage.feature_study_sage_app.data.local.dao.SessionDao
import com.example.studysage.feature_study_sage_app.data.local.dao.SubjectDao
import com.example.studysage.feature_study_sage_app.data.local.dao.TaskDao
import com.example.studysage.feature_study_sage_app.data.local.entity.SessionEntity
import com.example.studysage.feature_study_sage_app.data.local.entity.SubjectEntity
import com.example.studysage.feature_study_sage_app.data.local.entity.TaskEntity
import com.example.studysage.feature_study_sage_app.data.local.type_converter.ColorListConverter

@Database(
    entities = [
        SubjectEntity::class,
        TaskEntity::class,
        SessionEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(ColorListConverter::class)
abstract class StudySageDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun taskDao(): TaskDao
    abstract fun sessionDao(): SessionDao

    companion object {
        const val DATABASE_NAME = "study_sage.db"
    }
}