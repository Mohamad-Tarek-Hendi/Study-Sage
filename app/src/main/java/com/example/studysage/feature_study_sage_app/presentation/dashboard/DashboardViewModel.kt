package com.example.studysage.feature_study_sage_app.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
}