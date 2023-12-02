package com.example.studysage.feature_study_sage_app.domain.model

import androidx.compose.ui.graphics.Color
import com.example.studysage.core.presentation.theme.gradient1
import com.example.studysage.core.presentation.theme.gradient2
import com.example.studysage.core.presentation.theme.gradient5
import com.example.studysage.core.presentation.theme.gradient6

data class Subject(
    val id: Int? = null,
    val name: String? = null,
    val goalHours: Float? = null,
    val color: List<Color>? = emptyList()
) {
    companion object {
        val subjectCardColors =
            listOf(
                gradient1,
                gradient2,
                gradient5,
                gradient6
            )
    }
}
