package com.example.studysage.feature_study_sage_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.studysage.feature_study_sage_app.data.dao.SessionDao
import com.example.studysage.feature_study_sage_app.data.dao.SubjectDao
import com.example.studysage.feature_study_sage_app.data.dao.TaskDao
import com.example.studysage.feature_study_sage_app.data.entity.SessionEntity
import com.example.studysage.feature_study_sage_app.data.entity.SubjectEntity
import com.example.studysage.feature_study_sage_app.data.entity.TaskEntity
import com.example.studysage.feature_study_sage_app.data.type_converter.ColorListConverter

@Database(
    entities = [
        SubjectEntity::class,
        TaskEntity::class,
        SessionEntity::class
    ],
    version = 2
)
@TypeConverters(ColorListConverter::class)
abstract class StudySageDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun taskDao(): TaskDao
    abstract fun sessionDao(): SessionDao
}