package com.example.studysage.feature_study_sage_app.presentation.session

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.use_case.base.StudySageUseCases
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
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val studySageUseCases: StudySageUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SessionState())
    val state: StateFlow<SessionState> = _state.asStateFlow()

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    init {
        launchCombinedState()
    }

    fun onEvent(event: SessionEvent) {
        when (event) {
            SessionEvent.NotifyToUpdateSubject -> {
                notifyToUpdateSubject()
            }

            SessionEvent.OnDelete -> {
                deleteSession()
            }

            is SessionEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }

            is SessionEvent.OnRelatedSubjectChange -> {
                _state.update {
                    it.copy(
                        subjectId = event.subject.id,
                        relatedToSubject = event.subject.name
                    )
                }
            }

            is SessionEvent.SaveSession -> {
                saveNewSession(duration = event.duration)
            }

            is SessionEvent.UpdateSubjectIdAndRelatedSubject -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.relatedToSubject,
                        subjectId = event.subjectId
                    )
                }
            }
        }
    }

    private fun launchCombinedState() {
        viewModelScope.launch {
            try {
                val subjectList = studySageUseCases.getAllSubjectUseCase()
                val sessionList = studySageUseCases.getSessionListUseCase()
                combine(
                    _state,
                    subjectList,
                    sessionList
                ) { state, subjectList, sessionList ->
                    state.copy(
                        subjectList = subjectList,
                        sessionList = sessionList
                    )
                }.onEach { new_state ->
                    _state.value = new_state
                }.launchIn(this)
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Failed to load data. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }

        }
    }

    private fun saveNewSession(duration: Long) {
        viewModelScope.launch {
            if (duration < 36) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Each session can't be less than 36 seconds."
                    )
                )
                return@launch
            }
            try {
                studySageUseCases.saveSessionUseCase(
                    session = Session(
                        studySessionToSubject = state.value.subjectId ?: -1,
                        relatedStudySessionToSubject = state.value.relatedToSubject ?: "",
                        date = Instant.now().toEpochMilli(),
                        duration = duration
                    )
                )
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Session has been save successfully"
                    )
                )
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't save session. ${e.message}",
                        messageDuration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun deleteSession() {
        viewModelScope.launch {
            try {
                state.value.session?.let {
                    studySageUseCases.deleteSessionUseCase(session = it)
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

    private fun notifyToUpdateSubject() {
        viewModelScope.launch {
            if (state.value.subjectId != null && state.value.relatedToSubject != null) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Please select subject related to the session"
                    )
                )
            }
        }
    }
}