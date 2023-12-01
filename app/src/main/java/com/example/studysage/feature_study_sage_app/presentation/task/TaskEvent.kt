package com.example.studysage.feature_study_sage_app.presentation.task

import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.presentation.common.util.Priority

sealed class TaskEvent {
    data object SaveTask : TaskEvent()
    data object DeleteTask : TaskEvent()
    data object OnIsCompleteChange : TaskEvent()
    data class OnRelatedSubjectSelect(val subject: Subject?) : TaskEvent()
    data class OnPriorityChange(val priority: Priority) : TaskEvent()
    data class OnDateChange(val dateSelected: Long) : TaskEvent()
    data class OnDescriptionChange(val description: String) : TaskEvent()
    data class OnTitleChange(val title: String) : TaskEvent()
}
