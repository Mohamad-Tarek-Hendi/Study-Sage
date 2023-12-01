package com.example.studysage.feature_study_sage_app.presentation.common.converter

fun Int.pad(): String {
    return this.toString().padStart(length = 2, padChar = '0')
}