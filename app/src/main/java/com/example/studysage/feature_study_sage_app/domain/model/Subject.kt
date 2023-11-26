package com.example.studysage.feature_study_sage_app.domain.model

import androidx.compose.ui.graphics.Color
import com.example.studysage.core.presentation.theme.gradient1
import com.example.studysage.core.presentation.theme.gradient2
import com.example.studysage.core.presentation.theme.gradient3
import com.example.studysage.core.presentation.theme.gradient4
import com.example.studysage.core.presentation.theme.gradient5

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
                gradient3,
                gradient4,
                gradient5,
            )
    }
}
