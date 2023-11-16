package com.example.studysage.feature_study_sage_app.domain.model

import androidx.compose.ui.graphics.Color
import com.example.studysage.core.presentation.theme.gradient1
import com.example.studysage.core.presentation.theme.gradient2
import com.example.studysage.core.presentation.theme.gradient3
import com.example.studysage.core.presentation.theme.gradient4
import com.example.studysage.core.presentation.theme.gradient5

data class Subject(
    val id: Int,
    val name: String,
    val goalHours: Float,
    val color: List<Color>
) {
    companion object {
        val subjectCardColors =
            listOf(
                gradient1,
                gradient2,
                gradient3,
                gradient4,
                gradient5,
            )
    }
}
