package com.example.studysage.feature_study_sage_app.presentation.task

import androidx.lifecycle.ViewModel
import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
}