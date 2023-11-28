package com.example.studysage.feature_study_sage_app.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Subject

data class DashboardState(
    val totalSubjectCount: Int? = null,
    val totalStudiedHour: Float? = null,
    val totalGoalStudiedHour: Float? = null,
    val subjectList: List<Subject> = emptyList(),
    val subjectName: String? = null,
    val goalStudyHour: String? = null,
    val subjectCardColorList: List<Color> = Subject.subjectCardColors.random(),
    val session: Session? = null
)


