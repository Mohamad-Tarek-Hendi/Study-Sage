package com.example.studysage.feature_study_sage_app.presentation.subject_screen.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.studysage.R
import com.example.studysage.feature_study_sage_app.presentation.common.component.LargeTopAppBar
import com.example.studysage.feature_study_sage_app.presentation.common.component.PerformanceCard
import com.example.studysage.feature_study_sage_app.presentation.common.data.PerformanceCardItem
import com.example.studysage.feature_study_sage_app.presentation.subject_screen.component.CircularProgress

@Composable
fun SubjectScreen(

) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = stringResource(id = R.string.subject_screen_large_top_appbar),
                onBackButtonClick = { /*TODO*/ },
                onDeleteButtonClick = { /*TODO*/ },
                onEditButtonClick = {/*TODO*/ })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            item {
                PerformanceCardWithProgressSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    performanceCardsItems = listOf(
                        PerformanceCardItem(
                            name = stringResource(id = R.string.goal_study_hour),
                            count = "4.0"
                        ),
                        PerformanceCardItem(
                            name = stringResource(id = R.string.study_hour),
                            count = "0.0"
                        )
                    ),
                    progress = 0.75f
                )
            }

        }
    }
}


@Composable
fun PerformanceCardWithProgressSection(
    modifier: Modifier,
    performanceCardsItems: List<PerformanceCardItem>,
    progress: Float
) {
    val percentageProgressValue = remember(progress) {
        //Calculate and round to int and make sure the value between 0-100
        (progress * 100).toInt().coerceIn(0, 100)
    }
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(performanceCardsItems.size) { index ->
            PerformanceCard(
                headText = performanceCardsItems[index].name,
                countText = performanceCardsItems[index].count,
            )
        }
        item {
            CircularProgress(
                modifier = Modifier.size(75.dp),
                progress = progress,
                progressPercentageValue = percentageProgressValue
            )
        }
    }
}