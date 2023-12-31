package com.example.studysage.feature_study_sage_app.presentation.dashboard.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studysage.R
import com.example.studysage.core.presentation.theme.md_theme_dark_onPrimaryContainer
import com.example.studysage.feature_study_sage_app.domain.model.Session
import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.model.Task
import com.example.studysage.feature_study_sage_app.presentation.common.component.AddAndEditSubjectDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.DeleteDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.PerformanceCard
import com.example.studysage.feature_study_sage_app.presentation.common.component.studySessionList
import com.example.studysage.feature_study_sage_app.presentation.common.component.taskList
import com.example.studysage.feature_study_sage_app.presentation.common.data.PerformanceCardItem
import com.example.studysage.feature_study_sage_app.presentation.common.util.SnackBarEvent
import com.example.studysage.feature_study_sage_app.presentation.dashboard.DashboardEvent
import com.example.studysage.feature_study_sage_app.presentation.dashboard.DashboardState
import com.example.studysage.feature_study_sage_app.presentation.dashboard.DashboardViewModel
import com.example.studysage.feature_study_sage_app.presentation.dashboard.component.DashBoardScreenTopAppBar
import com.example.studysage.feature_study_sage_app.presentation.dashboard.component.SubjectCard
import com.example.studysage.feature_study_sage_app.presentation.destinations.SessionScreenRouteDestination
import com.example.studysage.feature_study_sage_app.presentation.destinations.SubjectScreenRouteDestination
import com.example.studysage.feature_study_sage_app.presentation.destinations.TaskScreenRouteDestination
import com.example.studysage.feature_study_sage_app.presentation.subject.base.SubjectScreenNavArgs
import com.example.studysage.feature_study_sage_app.presentation.task.base.TaskScreenNavArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@RootNavGraph(start = true)
@Destination()
@Composable
fun DashboardScreenRoute(
    navigator: DestinationsNavigator
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val taskList by viewModel.taskList.collectAsStateWithLifecycle()
    val sessionList by viewModel.sessionList.collectAsStateWithLifecycle()

    DashBoardScreen(
        state = state,
        taskList = taskList,
        sessionList = sessionList,
        snackBarEvent = viewModel.snackBarEventFlow,
        onEvent = viewModel::onEvent,
        onSubjectCardClick = { subjectId ->
            subjectId?.let {
                val navArg = SubjectScreenNavArgs(subjectId = subjectId)
                navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
            }
        },
        onTaskCardClick = { taskId ->
            taskId?.let {
                val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
                navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
            }
        },
        onStartStudySessionButtonClick = {
            navigator.navigate(SessionScreenRouteDestination())
        }
    )
}

@Composable
private fun DashBoardScreen(
    state: DashboardState,
    taskList: List<Task>,
    sessionList: List<Session>,
    snackBarEvent: SharedFlow<SnackBarEvent>,
    onEvent: (DashboardEvent) -> Unit,
    onSubjectCardClick: (Int?) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onStartStudySessionButtonClick: () -> Unit
) {

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    val snackBarState = remember { SnackbarHostState() }

    AddAndEditSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        selectedColor = state.subjectCardColorList,
        subjectName = state.subjectName ?: "",
        goalHour = state.goalStudyHour ?: "",
        onColorChange = { subjectColor ->
            onEvent(DashboardEvent.OnSubjectCardColorChange(subjectColor = subjectColor))
        },
        onSubjectNameValueChange = { subjectName ->
            onEvent(DashboardEvent.OnSubjectNameChange(subjectName = subjectName))
        },
        onGoalHourValueChange = { goalStudyHour ->
            onEvent(DashboardEvent.OnStudyHoursChange(goalStudyHours = goalStudyHour))
        },
        onDismissRequest = {
            isAddSubjectDialogOpen = false
        },
        onConfirmationClick = {
            onEvent(DashboardEvent.SaveSubject)
            isAddSubjectDialogOpen = false
        }
    )

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        deleteMessage = stringResource(id = R.string.delete_subject_message),
        onConfirmationClick = {
            onEvent(DashboardEvent.DeleteSession)
            isDeleteSessionDialogOpen = false
        },
        onDismissRequest = {
            isDeleteSessionDialogOpen = false
        }
    )

    LaunchedEffect(key1 = snackBarState) {
        snackBarEvent.collectLatest { event ->
            when (event) {
                is SnackBarEvent.ShowSnackBar -> {
                    snackBarState.showSnackbar(
                        message = event.message,
                        duration = event.messageDuration
                    )
                }

                SnackBarEvent.NavigateUp -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarState
            )
        },
        topBar =
        {
            DashBoardScreenTopAppBar()
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                PerformanceCardSection(
                    performanceCardsItems = listOf(
                        PerformanceCardItem(
                            name = "Subject Count",
                            count = state.totalSubjectCount.toString()
                        ),
                        PerformanceCardItem(
                            name = "Studied Hours",
                            count = state.totalStudiedHour.toString()
                        ),
                        PerformanceCardItem(
                            name = "Goal Study Hours",
                            count = state.totalGoalStudiedHour.toString()
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
            item {
                SubjectCardSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = state.subjectList,
                    emptyText = stringResource(id = R.string.hint_to_add_subject),
                    onClickAddSubjectButton = {
                        isAddSubjectDialogOpen = true
                    },
                    onSubjectCardClick = onSubjectCardClick
                )
            }
            item {
                FilledTonalButton(
                    onClick = {
                        onStartStudySessionButtonClick()
                    },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = md_theme_dark_onPrimaryContainer,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.start_study_session),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
            taskList(
                sectionTitle = "UpComing Task ",
                tasks = taskList,
                emptyText = "You don't have any task.\n Click the + button to add new task",
                onTaskCardClick = onTaskCardClick,
                onCheckBoxClick = { taskCheckBox ->
                    onEvent(DashboardEvent.OnTaskIsCompleteChanged(task = taskCheckBox))
                }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            studySessionList(
                sectionTitle = "Recent Session Study",
                sessions = sessionList,
                emptyText = "You don't have any study session.\n start a study session to begin recording your progress",
                onDeleteIconClick = { session ->
                    onEvent(DashboardEvent.OnDeleteSessionButtonClick(session = session))
                    isDeleteSessionDialogOpen = true
                }
            )
        }
    }
}


@Composable
fun PerformanceCardSection(
    modifier: Modifier,
    performanceCardsItems: List<PerformanceCardItem>
) {
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
    }
}

@Composable
fun SubjectCardSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyText: String,
    onClickAddSubjectButton: () -> Unit,
    onSubjectCardClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.subject),
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(start = 12.dp)
        )
        IconButton(onClick = onClickAddSubjectButton) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Subject"
            )
        }
    }
    if (subjectList.isEmpty()) {
        Column(modifier = modifier) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.translation),
                contentDescription = emptyText,
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = emptyText,
                style = MaterialTheme
                    .typography.bodySmall
                    .copy(
                        fontWeight = FontWeight.Bold
                    ),
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp)
            )
        }

    }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(start = 2.dp, end = 2.dp)
    ) {
        items(subjectList) { subject ->
            SubjectCard(
                subjectName = subject.name ?: "",
                gradient = subject.color ?: emptyList(),
                onClick = {
                    subject.id?.let {
                        onSubjectCardClick(it)
                    }
                }
            )
        }
    }
}