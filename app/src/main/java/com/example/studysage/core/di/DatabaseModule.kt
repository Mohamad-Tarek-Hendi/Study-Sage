package com.example.studysage.core.di

import android.app.Application
import androidx.room.Room
import com.example.studysage.feature_study_sage_app.data.local.dao.SessionDao
import com.example.studysage.feature_study_sage_app.data.local.dao.SubjectDao
import com.example.studysage.feature_study_sage_app.data.local.dao.TaskDao
import com.example.studysage.feature_study_sage_app.data.local.db.StudySageDatabase
import com.example.studysage.feature_study_sage_app.data.local.db.StudySageDatabase.Companion.DATABASE_NAME
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import com.example.studysage.feature_study_sage_app.domain.use_case.base.StudySageUseCases
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.DeleteSessionUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.GetRelatedSessionBySpecificSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.GetSessionListUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.GetTotalRelatedSessionDurationBySpecificSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.GetTotalSessionDurationUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.SaveSessionUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.DeleteSubjectByIdUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.GetAllSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.GetSubjectByIdUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.GetTotalGoalHourSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.GetTotalSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.UpsertSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.DeleteTaskByIdUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.GetRelatedCompletedTasksBySpecificSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.GetRelatedUpcomingTasksBySpecificSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.GetTaskByIdUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.GetTaskListUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.UpsertTaskUseCase
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

    @Provides
    @Singleton
    fun provideStudySageUseCases(
        subjectRepository: SubjectRepository,
        sessionRepository: SessionRepository,
        taskRepository: TaskRepository
    ): StudySageUseCases {
        return StudySageUseCases(
            upsertSubjectUseCase = UpsertSubjectUseCase(
                subjectRepository = subjectRepository
            ),
            getAllSubjectUseCase = GetAllSubjectUseCase(
                subjectRepository = subjectRepository
            ),
            getTotalSubjectUseCase = GetTotalSubjectUseCase(
                subjectRepository = subjectRepository
            ),
            getTotalGoalHourSubjectUseCase = GetTotalGoalHourSubjectUseCase(
                subjectRepository = subjectRepository
            ),
            getTotalSessionDurationUseCase = GetTotalSessionDurationUseCase(
                sessionRepository = sessionRepository
            ),
            getTaskListUseCase = GetTaskListUseCase(
                taskRepository = taskRepository
            ),
            getSessionListUseCase = GetSessionListUseCase(
                sessionRepository = sessionRepository
            ),
            upsertTaskUseCase = UpsertTaskUseCase(
                taskRepository = taskRepository
            ),
            deleteSessionUseCase = DeleteSessionUseCase(
                sessionRepository = sessionRepository
            ),
            getTaskByIdUseCase = GetTaskByIdUseCase(
                taskRepository = taskRepository
            ),
            getSubjectByIdUseCase = GetSubjectByIdUseCase(
                subjectRepository = subjectRepository
            ),
            deleteTaskByIdUseCase = DeleteTaskByIdUseCase(
                taskRepository = taskRepository
            ),
            getRelatedUpcomingTasksBySpecificSubjectUseCase = GetRelatedUpcomingTasksBySpecificSubjectUseCase(
                taskRepository = taskRepository
            ),
            getRelatedCompletedTasksBySpecificSubjectUseCase = GetRelatedCompletedTasksBySpecificSubjectUseCase(
                taskRepository = taskRepository
            ),
            getRelatedSessionBySpecificSubjectUseCase = GetRelatedSessionBySpecificSubjectUseCase(
                sessionRepository = sessionRepository
            ),
            getTotalRelatedSessionDurationBySpecificSubjectUseCase = GetTotalRelatedSessionDurationBySpecificSubjectUseCase(
                sessionRepository = sessionRepository
            ),
            deleteSubjectByIdUseCase = DeleteSubjectByIdUseCase(
                subjectRepository = subjectRepository
            ),
            saveSessionUseCase = SaveSessionUseCase(
                sessionRepository = sessionRepository
            )
        )
    }
}