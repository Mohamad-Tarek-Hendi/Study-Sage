package com.example.studysage.feature_study_sage_app.presentation.task

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studysage.feature_study_sage_app.domain.model.Task
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import com.example.studysage.feature_study_sage_app.presentation.common.util.Priority
import com.example.studysage.feature_study_sage_app.presentation.common.util.SnackBarEvent
import com.example.studysage.feature_study_sage_app.presentation.navArgs
import com.example.studysage.feature_study_sage_app.presentation.task.base.TaskScreenNavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val subjectRepository: SubjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs: TaskScreenNavArgs = savedStateHandle.navArgs()

    private val _state = MutableStateFlow(TaskState())

    val state = combine(
        _state,
        subjectRepository.getAllSubjectList()
    ) { state, subjectList ->
        state.copy(
            subjectList = subjectList
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TaskState()
    )

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    init {
        getTaskInformation()
        getSubject()
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.OnDateChange -> {
                _state.update {
                    it.copy(
                        date = event.dateSelected
                    )
                }
            }

            is TaskEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is TaskEvent.OnPriorityChange -> {
                _state.update {
                    it.copy(
                        priority = event.priority
                    )
                }
            }

            is TaskEvent.OnRelatedSubjectSelect -> {
                _state.update {
                    it.copy(
                        subjectId = event.subject?.id,
                        relatedTaskToSubject = event.subject?.name
                    )
                }
            }

            is TaskEvent.OnTitleChange -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            TaskEvent.OnIsCompleteChange -> {
                _state.update {
                    it.copy(
                        isTaskIsComplete = !state.value.isTaskIsComplete
                    )
                }
            }

            TaskEvent.DeleteTask -> deleteTasK()
            TaskEvent.SaveTask -> saveTask()
        }
    }


    private fun getTaskInformation() {
        viewModelScope.launch {
            val taskId = navArgs.taskId
            taskId?.let { id ->
                taskRepository.getTaskById(taskId = id)?.let { taskInfo ->
                    _state.update {
                        it.copy(
                            subjectId = taskInfo.taskSubjectId,
                            taskId = taskInfo.id,
                            title = taskInfo.title ?: "",
                            description = taskInfo.description ?: "",
                            date = taskInfo.date,
                            isTaskIsComplete = taskInfo.isTaskComplete ?: false,
                            relatedTaskToSubject = taskInfo.relatedTaskToSubject,
                            priority = Priority.fromInt(taskInfo.priority ?: 0)
                        )
                    }
                }
            }
        }
    }

    private fun getSubject() {
        viewModelScope.launch {
            val subjectId = navArgs.subjectId
            subjectId?.let { id ->
                subjectRepository.getSubjectById(subjectId = id)?.let { subject ->
                    _state.update {
                        it.copy(
                            subjectId = subject.id,
                            relatedTaskToSubject = subject.name
                        )
                    }
                }
            }
        }
    }

    private fun saveTask() {
        viewModelScope.launch {

            val state = _state.value
            if (state.subjectId == null || state.relatedTaskToSubject == null) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Please select subject that related to the specific task."
                    )
                )
                return@launch
            }
            try {
                taskRepository.upsertTask(
                    Task(
                        id = state.taskId,
                        taskSubjectId = state.subjectId,
                        title = state.title,
                        description = state.description,
                        date = state.date ?: Instant.now().toEpochMilli(),
                        priority = state.priority.value,
                        relatedTaskToSubject = state.relatedTaskToSubject,
                        isTaskComplete = state.isTaskIsComplete
                    )
                )
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "The task has been saved successfully."
                    )
                )
                _snackBarEventFlow.emit(SnackBarEvent.NavigateUp)

            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't save task. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun deleteTasK() {
        viewModelScope.launch {
            try {
                val currentTaskId = state.value.taskId
                if (currentTaskId != null) {
                    withContext(Dispatchers.IO) {
                        taskRepository.deleteTaskById(taskId = currentTaskId)
                    }
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "Task has been deleted successfully"
                        )
                    )
                    _snackBarEventFlow.emit(
                        SnackBarEvent.NavigateUp
                    )
                } else {
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "Task can't been deleted"
                        )
                    )
                }
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't delete task. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }
        }

    }

}