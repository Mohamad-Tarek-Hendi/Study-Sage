package com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case

import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteTaskByIdUseCase(
    private val taskRepository: TaskRepository,
    //we recommend injecting Dispatchers For easier testing
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(taskId: Int) {
        withContext(defaultDispatcher) {
            taskRepository.deleteTaskById(taskId = taskId)
        }
    }
}
