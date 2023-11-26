package com.example.studysage.feature_study_sage_app.presentation.session

import androidx.lifecycle.ViewModel
import com.example.studysage.feature_study_sage_app.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

}