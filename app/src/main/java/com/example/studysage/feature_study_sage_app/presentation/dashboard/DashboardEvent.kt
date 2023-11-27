package com.example.studysage.feature_study_sage_app.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Task

sealed class DashboardEvent {
    data object SaveSubject : DashboardEvent()
    data object DeleteSession : DashboardEvent()
    data class OnDeleteSessionButtonClick(val session: Session) : DashboardEvent()
    data class OnTaskIsCompleteChanged(val task: Task) : DashboardEvent()
    data class OnSubjectCardColorChange(val subjectColor: List<Color>) : DashboardEvent()
    data class OnSubjectNameChange(val subjectName: String) : DashboardEvent()
    data class OnStudyHoursChange(val goalStudyHours: String) : DashboardEvent()
}
