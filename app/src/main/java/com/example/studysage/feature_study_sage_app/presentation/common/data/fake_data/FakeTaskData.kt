package com.example.studysage.feature_study_sage_app.presentation.common.data.fake_data

import com.example.studysage.feature_study_sage_app.domain.model.Task

object FakeTaskData {

    val FakeTaskList = listOf(

        Task(
            title = "Prepare Note",
            description = "",
            date = 0L,
            priority = 1,
            relatedTaskToSubject = "",
            isTaskComplete = true,
            taskSubjectId = 0,
            id = 1
        ),
        Task(
            title = "Prepare Note",
            description = "",
            date = 0L,
            priority = 0,
            relatedTaskToSubject = "",
            isTaskComplete = false,
            taskSubjectId = 0,
            id = 1
        )
    )
}