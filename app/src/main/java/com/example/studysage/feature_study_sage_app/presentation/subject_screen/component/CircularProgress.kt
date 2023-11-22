package com.example.studysage.feature_study_sage_app.presentation.subject_screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    progressPercentageValue: Int
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = progress,
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = progress,
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round
        )
        Text(
            text = "$progressPercentageValue%"
        )
    }
}