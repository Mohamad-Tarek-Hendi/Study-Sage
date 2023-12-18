package com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case

import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetAllSubjectUseCase(
    private val subjectRepository: SubjectRepository,
    //we recommend injecting Dispatchers For easier testing
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): Flow<List<Subject>> {
        return withContext(ioDispatcher) {
            subjectRepository.getAllSubjectList()
        }
    }
}
