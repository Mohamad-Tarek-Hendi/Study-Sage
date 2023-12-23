package com.example.studysage.core.di

import com.example.studysage.feature_study_sage_app.data.local.repository.SessionRepositoryImp
import com.example.studysage.feature_study_sage_app.data.local.repository.SubjectRepositoryImp
import com.example.studysage.feature_study_sage_app.data.local.repository.TaskRepositoryImp
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    //Useful when you have an interface and multiple implementations
    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImp
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImp
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImp
    ): SessionRepository
}