package com.example.studysage.feature_study_sage_app.presentation.subject

import androidx.compose.ui.graphics.Color
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.model.Task

data class SubjectState(
    val currentSubjectId: Int? = null,
    val subjectName: String? = null,
    val goalStudyHour: String? = null,
    val subjectCardColorList: List<Color> = Subject.subjectCardColors.random(),
    val studyHour: Float? = null,
    val progress: Float? = null,
    val upcomingTaskList: List<Task>? = emptyList(),
    val completedTaskList: List<Task>? = emptyList(),
    val recentSessionList: List<Session>? = emptyList(),
    val session: Session? = null,
    val isLoading: Boolean? = false,
)