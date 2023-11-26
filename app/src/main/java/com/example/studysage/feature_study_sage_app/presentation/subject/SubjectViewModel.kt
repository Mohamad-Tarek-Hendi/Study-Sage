package com.example.studysage.feature_study_sage_app.presentation.subject

import androidx.lifecycle.ViewModel
import com.example.studysage.feature_study_sage_app.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
) : ViewModel() {

}