package com.example.studysage.feature_study_sage_app.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Subject

data class DashboardState(
    val totalSubjectCount: Int? = 0,
    val totalStudiedHour: Float? = 0f,
    val totalGoalStudiedHour: Float? = 0f,
    val subjectList: List<Subject> = emptyList(),
    val subjectName: String? = null,
    val goalStudyHour: String? = null,
    val subjectCardColorList: List<Color> = Subject.subjectCardColors.random(),
    val session: Session? = null
)


