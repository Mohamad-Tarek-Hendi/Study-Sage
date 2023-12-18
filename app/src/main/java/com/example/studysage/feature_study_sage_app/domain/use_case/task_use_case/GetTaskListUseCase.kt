package com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case

import com.example.studysage.feature_study_sage_app.domain.model.Task
import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetTaskListUseCase(
    private val taskRepository: TaskRepository,
    //we recommend injecting Dispatchers For easier testing
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): Flow<List<Task>> {
        return withContext(ioDispatcher) {
            taskRepository.getTaskList()
        }
    }
}
