package com.example.studysage.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.studysage.core.presentation.theme.StudySageTheme
import com.example.studysage.feature_study_sage_app.presentation.subject.base.SubjectScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudySageTheme {
                SubjectScreen()
            }
        }
    }
}