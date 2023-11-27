package com.example.studysage.feature_study_sage_app.presentation.common.util

import androidx.compose.material3.SnackbarDuration

sealed class SnackBarEvent {
    data class ShowSnackBar(
        val message: String,
        val messageDuration: SnackbarDuration = SnackbarDuration.Short
    ) : SnackBarEvent()
}
