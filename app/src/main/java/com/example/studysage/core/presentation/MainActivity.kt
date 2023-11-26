package com.example.studysage.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.studysage.core.presentation.theme.StudySageTheme
import com.example.studysage.feature_study_sage_app.presentation.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudySageTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}