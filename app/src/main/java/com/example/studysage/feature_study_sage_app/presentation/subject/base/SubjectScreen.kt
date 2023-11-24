@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.studysage.feature_study_sage_app.presentation.subject.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.studysage.R
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.model.Task
import com.example.studysage.feature_study_sage_app.presentation.common.component.AddSubjectDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.DeleteDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.PerformanceCard
import com.example.studysage.feature_study_sage_app.presentation.common.component.studySessionList
import com.example.studysage.feature_study_sage_app.presentation.common.component.taskList
import com.example.studysage.feature_study_sage_app.presentation.common.data.PerformanceCardItem
import com.example.studysage.feature_study_sage_app.presentation.destinations.TaskScreenRouteDestination
import com.example.studysage.feature_study_sage_app.presentation.subject.component.CircularProgress
import com.example.studysage.feature_study_sage_app.presentation.subject.component.LargeTopAppBar
import com.example.studysage.feature_study_sage_app.presentation.task.base.TaskScreenNavArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class SubjectScreenNavArgs(
    val subjectId: Int
)

@Destination(navArgsDelegate = SubjectScreenNavArgs::class)
@Composable
fun SubjectScreenRoute(
    navigator: DestinationsNavigator
) {
    SubjectScreen(
        onBackButtonClick = {
            navigator.navigateUp()
        },
        onAddTaskFloatingButtonClick = {
            navigator.navigate(TaskScreenRouteDestination(taskId = null, subjectId = -1))
        },
        onTaskCardClick = { taskId ->
            taskId?.let {
                val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
                navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
            }
        }
    )
}

@Composable
private fun SubjectScreen(
    onBackButtonClick: () -> Unit,
    onAddTaskFloatingButtonClick: () -> Unit,
    onTaskCardClick: (Int?) -> Unit
) {
    //Fake Data
    val taskList =
        listOf(
            Task(
                id = 1,
                taskSubjectId = 0,
                title = "Prepare first 5 pages",
                description = "",
                date = 0L,
                priority = 0,
                relatedTaskToSubject = "",
                isTaskComplete = false
            ),
            Task(
                id = 1,
                taskSubjectId = 0,
                title = "Prepare second 5 pages",
                description = "",
                date = 0L,
                priority = 1,
                relatedTaskToSubject = "",
                isTaskComplete = true
            ),
            Task(
                id = 1,
                taskSubjectId = 0,
                title = "Prepare second 5 pages",
                description = "",
                date = 0L,
                priority = 1,
                relatedTaskToSubject = "",
                isTaskComplete = true
            ),
            Task(
                id = 1,
                taskSubjectId = 0,
                title = "Prepare second 5 pages",
                description = "",
                date = 0L,
                priority = 1,
                relatedTaskToSubject = "",
                isTaskComplete = true
            ),
            Task(
                id = 1,
                taskSubjectId = 0,
                title = "Prepare second 5 pages",
                description = "",
                date = 0L,
                priority = 1,
                relatedTaskToSubject = "",
                isTaskComplete = true
            )
        )

    val sessionLists =
        listOf(
            Session(
                id = 0,
                studySessionToSubject = 0,
                relatedStudySessionToSubject = "English",
                date = 2,
                duration = 0L
            ),
            Session(
                id = 0,
                studySessionToSubject = 0,
                relatedStudySessionToSubject = "Math",
                date = 2,
                duration = 0L
            )
        )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val lazyColumnState = rememberLazyListState()

    val isFloatingActionButtonExpanded by remember {
        derivedStateOf { lazyColumnState.firstVisibleItemIndex == 0 }
    }

    var isEditSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    var selectedColor by remember { mutableStateOf(Subject.subjectCardColors.random()) }

    var subjectName by remember { mutableStateOf("") }

    var goalHour by remember { mutableStateOf("") }

    AddSubjectDialog(
        isOpen = isEditSubjectDialogOpen,
        selectedColor = selectedColor,
        subjectName = subjectName,
        goalHour = goalHour,
        onColorChange = { selectedColor = it },
        onSubjectNameValueChange = { subjectName = it },
        onGoalHourValueChange = { goalHour = it },
        onDismissRequest = { isEditSubjectDialogOpen = false },
        onConfirmationClick = { isEditSubjectDialogOpen = false }
    )

    DeleteDialog(
        isOpen = isDeleteSubjectDialogOpen,
        deleteMessage = stringResource(id = R.string.delete_subject_message),
        onConfirmationClick = { isDeleteSubjectDialogOpen = false },
        onDismissRequest = { isDeleteSubjectDialogOpen = false }
    )

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        deleteMessage = stringResource(id = R.string.delete_session_message),
        onConfirmationClick = { isDeleteSessionDialogOpen = false },
        onDismissRequest = { isDeleteSessionDialogOpen = false }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = scrollBehavior,
                title = stringResource(id = R.string.subject_screen_large_top_appbar),
                onBackButtonClick = { onBackButtonClick() },
                onDeleteButtonClick = { isDeleteSubjectDialogOpen = true },
                onEditButtonClick = { isEditSubjectDialogOpen = true })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onAddTaskFloatingButtonClick() },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task") },
                text = { Text(text = "Add Task") },
                expanded = isFloatingActionButtonExpanded
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = lazyColumnState
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
            taskList(
                sectionTitle = "UpComing Task ",
                tasks = taskList,
                emptyText = "You don't have any task.\n Click the + button to add new task",
                onTaskCardClick = onTaskCardClick,
                onCheckBoxClick = {/*TODO*/ }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            taskList(
                sectionTitle = "Complete Task ",
                tasks = taskList,
                emptyText = "You don't have any Complete task.\n Click the check box to complete task.",
                onTaskCardClick = {/*TODO*/ },
                onCheckBoxClick = {/*TODO*/ }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            studySessionList(
                sectionTitle = "Recent Session Study",
                sessions = sessionLists,
                emptyText = "You don't have any study session.\n start a study session to begin recording your progress",
                onDeleteIconClick = { isDeleteSessionDialogOpen = true }
            )
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
                modifier = Modifier
                    .fillMaxWidth(),
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