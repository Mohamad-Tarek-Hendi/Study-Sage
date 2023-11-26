package com.example.studysage.feature_study_sage_app.data.mapper

import com.example.studysage.feature_study_sage_app.data.entity.TaskEntity
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
        taskSubjectId = taskSubjectId,
        title = title,
        description = description,
        date = date,
        priority = priority,
        relatedTaskToSubject = relatedTaskToSubject,
        isTaskComplete = isTaskComplete
    )
}