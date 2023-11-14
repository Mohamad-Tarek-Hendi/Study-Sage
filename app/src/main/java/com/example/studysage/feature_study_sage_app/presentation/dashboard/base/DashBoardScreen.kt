package com.example.studysage.feature_study_sage_app.presentation.dashboard.base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.studysage.feature_study_sage_app.presentation.common.component.TopAppBar

@Composable
fun DashBoardScreen() {

    Scaffold(
        topBar =
        {
            TopAppBar()
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
        }

    }

}