package com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case

import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteSessionUseCase(
    private val sessionRepository: SessionRepository,
    //we recommend injecting Dispatchers For easier testing
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(session: Session) {
        withContext(defaultDispatcher) {
            sessionRepository.deleteSession(session = session)
        }
    }
}
