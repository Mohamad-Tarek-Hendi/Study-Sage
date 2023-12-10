package com.example.studysage.feature_study_sage_app.data.local.mapper

import com.example.studysage.feature_study_sage_app.data.local.entity.TaskEntity
import com.example.studysage.feature_study_sage_app.domain.model.Task

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        taskSubjectId = taskSubjectId,
        title = title,
        description = description,
        date = date,
        priority = priority,
        relatedTaskToSubject = relatedTaskToSubject,
        isTaskComplete = isTaskComplete
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        taskSubjectId = taskSubjectId,
        title = title,
        description = description,
        date = date,
        priority = priority,
        relatedTaskToSubject = relatedTaskToSubject,
        isTaskComplete = isTaskComplete
    )
}