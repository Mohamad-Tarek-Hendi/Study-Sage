package com.example.studysage.feature_study_sage_app.presentation.subject

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import com.example.studysage.feature_study_sage_app.domain.repository.TaskRepository
import com.example.studysage.feature_study_sage_app.presentation.common.mapping.toHours
import com.example.studysage.feature_study_sage_app.presentation.common.util.SnackBarEvent
import com.example.studysage.feature_study_sage_app.presentation.navArgs
import com.example.studysage.feature_study_sage_app.presentation.subject.base.SubjectScreenNavArgs
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
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val taskRepository: TaskRepository,
    private val sessionRepository: SessionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs: SubjectScreenNavArgs = savedStateHandle.navArgs()

    private val _state = MutableStateFlow(SubjectState())

    val state = combine(
        _state,
        taskRepository.getRelatedUpcomingTasksBySpecificSubject(navArgs.subjectId),
        taskRepository.getRelatedCompletedTasksBySpecificSubject(navArgs.subjectId),
        sessionRepository.getRelatedSessionBySpecificSubject(navArgs.subjectId),
        sessionRepository.getTotalRelatedSessionDurationBySpecificSubject(navArgs.subjectId),
    ) { _state, upcomingTaskList, completedTaskList, relatedSessionBySpecificSubjectList, totalRelatedSessionBySpecificSubjectList ->
        _state.copy(
            upcomingTaskList = upcomingTaskList,
            completedTaskList = completedTaskList,
            recentSessionList = relatedSessionBySpecificSubjectList,
            studyHour = totalRelatedSessionBySpecificSubjectList.toHours(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SubjectState()
    )

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    init {
        getSubject()
    }

    fun onEvent(event: SubjectEvent) {
        when (event) {
            SubjectEvent.DeleteSession -> TODO()
            SubjectEvent.DeleteSubject -> deleteSubject()
            SubjectEvent.UpdateSubject -> updateSubject()
            SubjectEvent.UpdateProgress -> {
                val goalStudyHour = state.value.goalStudyHour?.toFloatOrNull() ?: 1f
                _state.update {
                    it.copy(
                        progress = (state.value.studyHour?.div(goalStudyHour))?.coerceIn(0f, 1f)
                    )
                }
            }

            is SubjectEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(
                        subjectCardColorList = event.colorList
                    )
                }
            }

            is SubjectEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(
                        subjectName = event.subjectName
                    )
                }
            }

            is SubjectEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(
                        goalStudyHour = event.goalStudyHours
                    )
                }
            }

            is SubjectEvent.OnDeleteSubjectButtonClick -> TODO()
            is SubjectEvent.OnTaskIsCompleteChange -> TODO()
        }
    }

    private fun getSubject() {
        viewModelScope.launch {
            subjectRepository.getSubjectById(navArgs.subjectId)?.let { subject ->
                _state.update {
                    it.copy(
                        currentSubjectId = subject.id,
                        subjectName = subject.name,
                        goalStudyHour = subject.goalHours.toString(),
                        subjectCardColorList = subject.color ?: emptyList()
                    )
                }
            }
        }
    }

    private fun updateSubject() {
        viewModelScope.launch {

            try {
                subjectRepository.upsertSubject(
                    subject = Subject(
                        id = state.value.currentSubjectId,
                        name = state.value.subjectName,
                        goalHours = state.value.goalStudyHour?.toFloatOrNull(),
                        color = state.value.subjectCardColorList
                    )
                )
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "The subject has been updated successfully."
                    )
                )
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't update subject. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun deleteSubject() {
        viewModelScope.launch {
            try {
                val subjectId = state.value.currentSubjectId
                if (subjectId != null) {
                    //These operations can be time-consuming and
                    //should not be performed on the main UI thread to avoid blocking the user interface.
                    withContext(Dispatchers.IO) {
                        subjectRepository.deleteSubject(subjectId = subjectId)
                    }
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "The subject has been deleted successfully."
                        )
                    )
                    _snackBarEventFlow.emit(
                        SnackBarEvent.NavigateUp
                    )
                } else {
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "There's no subject to deleted."
                        )
                    )
                }
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't deleted subject. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
}