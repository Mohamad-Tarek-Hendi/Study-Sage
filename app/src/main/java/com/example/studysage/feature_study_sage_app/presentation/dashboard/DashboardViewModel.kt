package com.example.studysage.feature_study_sage_app.presentation.dashboard

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.model.Task
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import com.example.studysage.feature_study_sage_app.presentation.common.mapping.toHours
import com.example.studysage.feature_study_sage_app.presentation.common.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val taskRepository: TaskRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())

    val state = combine(
        _state,
        subjectRepository.getTotalSubjectCount(),
        subjectRepository.getTotalGoalHour(),
        subjectRepository.getAllSubjectList(),
        sessionRepository.getTotalSessionDuration()
    ) { _state, totalSubjectCount, totalGoalHour, subjects, totalSessionDuration ->

        _state.copy(
            totalSubjectCount = totalSubjectCount,
            totalGoalStudiedHour = totalGoalHour,
            subjectList = subjects,
            totalStudiedHour = totalSessionDuration.toHours()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardState()
    )

    private val _tasksList: StateFlow<List<Task>> = taskRepository.getTaskList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val taskList: StateFlow<List<Task>>
        get() = _tasksList

    private val _sessionList: StateFlow<List<Session>> = sessionRepository.getSessionList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val sessionList: StateFlow<List<Session>>
        get() = _sessionList

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }

            is DashboardEvent.OnStudyHoursChange -> {
                _state.update {
                    it.copy(
                        goalStudyHour = event.goalStudyHours
                    )
                }
            }

            is DashboardEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(
                        subjectCardColorList = event.subjectColor
                    )
                }
            }

            is DashboardEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(
                        subjectName = event.subjectName
                    )
                }
            }

            is DashboardEvent.OnTaskIsCompleteChanged -> TODO()
            DashboardEvent.DeleteSession -> TODO()
            DashboardEvent.SaveSubject -> {
                saveSubject()
            }

            else -> {}
        }
    }

    private fun saveSubject() {
        viewModelScope.launch {
            try {
                subjectRepository.upsertSubject(
                    subject = Subject(
                        name = state.value.subjectName,
                        goalHours = state.value.goalStudyHour?.toFloatOrNull() ?: 1f,
                        color = state.value.subjectCardColorList,
                    )
                )
                _state.update {
                    it.copy(
                        subjectName = null,
                        goalStudyHour = null,
                        subjectCardColorList = Subject.subjectCardColors.random()
                    )
                }
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "The subject has been saved successfully."
                    )
                )
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't save subject. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )

            }

        }
    }
}