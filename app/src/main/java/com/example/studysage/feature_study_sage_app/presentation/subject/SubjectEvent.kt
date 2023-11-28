package com.example.studysage.feature_study_sage_app.presentation.subject

import androidx.compose.ui.graphics.Color
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Task

sealed class SubjectEvent {
    data object UpdateSubject : SubjectEvent()
    data object DeleteSubject : SubjectEvent()
    data object DeleteSession : SubjectEvent()
    data object UpdateProgress : SubjectEvent()
    data class OnTaskIsCompleteChange(val task: Task) : SubjectEvent()
    data class OnSubjectCardColorChange(val colorList: List<Color>) : SubjectEvent()
    data class OnSubjectNameChange(val subjectName: String) : SubjectEvent()
    data class OnGoalStudyHoursChange(val goalStudyHours: String) : SubjectEvent()
    data class OnDeleteSubjectButtonClick(val session: Session) : SubjectEvent()
}
