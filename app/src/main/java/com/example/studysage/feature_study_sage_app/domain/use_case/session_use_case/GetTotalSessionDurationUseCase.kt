package com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case

import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetTotalSessionDurationUseCase(
    private val sessionRepository: SessionRepository,
    //we recommend injecting Dispatchers For easier testing
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Flow<Long> {
        return withContext(ioDispatcher) {
            sessionRepository.getTotalSessionDuration()
        }
    }
}