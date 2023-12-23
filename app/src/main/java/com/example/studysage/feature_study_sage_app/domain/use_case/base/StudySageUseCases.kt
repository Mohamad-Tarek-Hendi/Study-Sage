package com.example.studysage.feature_study_sage_app.domain.use_case.base

import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.DeleteSessionUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.GetRelatedSessionBySpecificSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.GetSessionListUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.GetTotalRelatedSessionDurationBySpecificSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.GetTotalSessionDurationUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.session_use_case.SaveSessionUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.DeleteSubjectByIdUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.GetAllSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.GetSubjectByIdUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.GetTotalGoalHourSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.GetTotalSubjectCountUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.subject_use_case.UpsertSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.DeleteTaskByIdUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.GetRelatedCompletedTasksBySpecificSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.GetRelatedUpcomingTasksBySpecificSubjectUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.GetTaskByIdUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.GetTaskListUseCase
import com.example.studysage.feature_study_sage_app.domain.use_case.task_use_case.UpsertTaskUseCase

data class StudySageUseCases(
    val upsertSubjectUseCase: UpsertSubjectUseCase,
    val getAllSubjectUseCase: GetAllSubjectUseCase,
    val getTotalSubjectCountUseCase: GetTotalSubjectCountUseCase,
    val getTotalGoalHourSubjectUseCase: GetTotalGoalHourSubjectUseCase,
    val getSubjectByIdUseCase: GetSubjectByIdUseCase,
    val deleteSubjectByIdUseCase: DeleteSubjectByIdUseCase,
    val getTaskListUseCase: GetTaskListUseCase,
    val upsertTaskUseCase: UpsertTaskUseCase,
    val getTaskByIdUseCase: GetTaskByIdUseCase,
    val getTotalSessionDurationUseCase: GetTotalSessionDurationUseCase,
    val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
    val getRelatedUpcomingTasksBySpecificSubjectUseCase: GetRelatedUpcomingTasksBySpecificSubjectUseCase,
    val getRelatedCompletedTasksBySpecificSubjectUseCase: GetRelatedCompletedTasksBySpecificSubjectUseCase,
    val getSessionListUseCase: GetSessionListUseCase,
    val deleteSessionUseCase: DeleteSessionUseCase,
    val getRelatedSessionBySpecificSubjectUseCase: GetRelatedSessionBySpecificSubjectUseCase,
    val getTotalRelatedSessionDurationBySpecificSubjectUseCase: GetTotalRelatedSessionDurationBySpecificSubjectUseCase,
    val saveSessionUseCase: SaveSessionUseCase
)
