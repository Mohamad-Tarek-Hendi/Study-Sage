package com.example.studysage.feature_study_sage_app.presentation.dashboard.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.studysage.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreenTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.top_app_bar_name),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}