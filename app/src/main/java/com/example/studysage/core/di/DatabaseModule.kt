package com.example.studysage.core.di

import android.app.Application
import androidx.room.Room
import com.example.studysage.feature_study_sage_app.data.dao.SessionDao
import com.example.studysage.feature_study_sage_app.data.dao.SubjectDao
import com.example.studysage.feature_study_sage_app.data.dao.TaskDao
import com.example.studysage.feature_study_sage_app.data.db.StudySageDatabase
import com.example.studysage.feature_study_sage_app.data.db.StudySageDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): StudySageDatabase {
        return Room
            .databaseBuilder(
                application,
                StudySageDatabase::class.java,
                DATABASE_NAME
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideSubjectDao(studySageDatabase: StudySageDatabase): SubjectDao {
        return studySageDatabase.subjectDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(studySageDatabase: StudySageDatabase): TaskDao {
        return studySageDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideSessionDao(studySageDatabase: StudySageDatabase): SessionDao {
        return studySageDatabase.sessionDao()
    }
}