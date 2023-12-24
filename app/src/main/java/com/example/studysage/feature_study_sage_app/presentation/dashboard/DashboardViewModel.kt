package com.example.studysage.feature_study_sage_app.presentation.dashboard

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.model.Task
import com.example.studysage.feature_study_sage_app.domain.use_case.base.StudySageUseCases
import com.example.studysage.feature_study_sage_app.presentation.common.converter.toHours
import com.example.studysage.feature_study_sage_app.presentation.common.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val studySageUseCases: StudySageUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    private val _tasksList: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val taskList: StateFlow<List<Task>> = _tasksList.asStateFlow()

    private val _sessionList: MutableStateFlow<List<Session>> = MutableStateFlow(emptyList())
    val sessionList: StateFlow<List<Session>> = _sessionList.asStateFlow()

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    init {
        launchCombinedState()
        getTaskList()
        getSessionList()
    }

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

            is DashboardEvent.OnTaskIsCompleteChanged -> {
                updateTask(event.task)
            }

            DashboardEvent.DeleteSession -> {
                deleteSession()
            }

            DashboardEvent.SaveSubject -> {
                saveSubject()
            }

        }
    }

    private fun launchCombinedState() {
        viewModelScope.launch {
            try {
                val totalSubjectCount = studySageUseCases.getTotalSubjectCountUseCase()
                val totalGoalHour = studySageUseCases.getTotalGoalHourSubjectUseCase()
                val subjectList = studySageUseCases.getAllSubjectUseCase()
                val totalSessionDuration = studySageUseCases.getTotalSessionDurationUseCase()

                combine(
                    _state,
                    totalSubjectCount,
                    totalGoalHour,
                    subjectList,
                    totalSessionDuration
                ) { _state, totalSubjectCount, totalGoalHour, subjectList, totalSessionDuration ->

                    _state.copy(
                        totalSubjectCount = totalSubjectCount,
                        totalGoalStudiedHour = totalGoalHour,
                        subjectList = subjectList,
                        totalStudiedHour = totalSessionDuration.toHours()
                    )
                }.onEach { news_state ->
                    _state.value = news_state
                }.launchIn(this)
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't Retrieve. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }

        }
    }

    private fun getTaskList() {
        viewModelScope.launch {
            try {
                studySageUseCases.getTaskListUseCase().onEach { tasks ->
                    _tasksList.value = tasks
                }.launchIn(this)
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't Retrieve Task. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun getSessionList() {
        viewModelScope.launch {
            try {
                studySageUseCases.getSessionListUseCase().onEach { session ->
                    _sessionList.value = session
                }.launchIn(this)
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't Retrieve Session. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun saveSubject() {
        viewModelScope.launch {
            val state = _state.value
            try {
                studySageUseCases.upsertSubjectUseCase(
                    subject = Subject(
                        name = state.subjectName,
                        goalHours = state.goalStudyHour?.toFloatOrNull() ?: 1f,
                        color = state.subjectCardColorList
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

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                studySageUseCases.upsertTaskUseCase(
                    task = task.copy(
                        isTaskComplete = !task.isTaskComplete!!
                    )
                )
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Task update successfully"
                    )
                )
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't update task. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun deleteSession() {
        viewModelScope.launch {
            try {
                state.value.session?.let { session ->
                    studySageUseCases.deleteSessionUseCase(session = session)
                }
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Session has been delete successfully"
                    )
                )
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't delete session. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
}