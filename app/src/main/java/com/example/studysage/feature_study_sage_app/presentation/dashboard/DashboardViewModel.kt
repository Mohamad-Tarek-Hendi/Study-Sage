package com.example.studysage.feature_study_sage_app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import com.example.studysage.feature_study_sage_app.presentation.common.mapping.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
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
}