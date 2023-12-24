package com.example.studysage.feature_study_sage_app.presentation.session.base

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studysage.R
import com.example.studysage.core.presentation.theme.Red
import com.example.studysage.feature_study_sage_app.presentation.common.component.DeleteDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.SubjectDropBottomSheet
import com.example.studysage.feature_study_sage_app.presentation.common.component.studySessionList
import com.example.studysage.feature_study_sage_app.presentation.common.util.Constant.ACTION_SERVICE_CANCEL
import com.example.studysage.feature_study_sage_app.presentation.common.util.Constant.ACTION_SERVICE_START
import com.example.studysage.feature_study_sage_app.presentation.common.util.Constant.ACTION_SERVICE_STOP
import com.example.studysage.feature_study_sage_app.presentation.common.util.SnackBarEvent
import com.example.studysage.feature_study_sage_app.presentation.session.SessionEvent
import com.example.studysage.feature_study_sage_app.presentation.session.SessionState
import com.example.studysage.feature_study_sage_app.presentation.session.SessionViewModel
import com.example.studysage.feature_study_sage_app.presentation.session.component.SessionTopAppBar
import com.example.studysage.feature_study_sage_app.presentation.session.component.TimerSession
import com.example.studysage.feature_study_sage_app.presentation.session.service.ServiceHelper
import com.example.studysage.feature_study_sage_app.presentation.session.service.StudySessionTimerService
import com.example.studysage.feature_study_sage_app.presentation.session.service.TimerState
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit

@Destination(
    deepLinks = [
        DeepLink(
            action = Intent.ACTION_VIEW,
            uriPattern = "study_smart://dashboard/session"
        )
    ]
)
@Composable
fun SessionScreenRoute(
    navigator: DestinationsNavigator,
    timerService: StudySessionTimerService
) {
    val viewModel: SessionViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarEventFlowState = viewModel.snackBarEventFlow
    val onEvent = viewModel::onEvent
    SessionScreen(
        state = state,
        snackBarEvent = snackBarEventFlowState,
        onEvent = onEvent,
        onBackButtonClick = {
            navigator.navigateUp()
        },
        timerService = timerService
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreen(
    state: SessionState,
    snackBarEvent: SharedFlow<SnackBarEvent>,
    onEvent: (SessionEvent) -> Unit,
    onBackButtonClick: () -> Unit,
    timerService: StudySessionTimerService
) {
    val context = LocalContext.current
    val hours by timerService.hours
    val minutes by timerService.minutes
    val seconds by timerService.seconds
    val currentTimerState by timerService.currentTimerState

    val scope = rememberCoroutineScope()

    val modalBottomSheetState = rememberModalBottomSheetState()

    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    var isSubjectDropBottomSheet by remember { mutableStateOf(false) }

    val snackBarState = remember { SnackbarHostState() }

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        deleteMessage = stringResource(id = R.string.delete_session_message_in_session_screen),
        onConfirmationClick = {
            onEvent(SessionEvent.OnDelete)
            isDeleteSessionDialogOpen = false
        },
        onDismissRequest = {
            isDeleteSessionDialogOpen = false
        }
    )

    SubjectDropBottomSheet(
        sheetState = modalBottomSheetState,
        isOpen = isSubjectDropBottomSheet,
        subjectList = state.subjectList,
        onSubjectClick = { subject ->
            scope.launch {
                modalBottomSheetState.hide()
            }.invokeOnCompletion {
                if (!modalBottomSheetState.isVisible) {
                    isSubjectDropBottomSheet = false
                }
                onEvent(SessionEvent.OnRelatedSubjectChange(subject = subject))
            }
        },
        onDismissRequest = { isSubjectDropBottomSheet = false }
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

    LaunchedEffect(key1 = state.subjectList) {
        val subjectId = timerService.subjectId.value
        onEvent(
            SessionEvent.UpdateSubjectIdAndRelatedSubject(
                subjectId = subjectId,
                relatedToSubject = state.subjectList.find {
                    it.id == subjectId
                }?.name
            )
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarState)
        },
        topBar = {
            SessionTopAppBar(
                title = stringResource(R.string.session_top_appbar_title),
                onBackButtonClick = { onBackButtonClick() }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            item {
                TimerSession(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds
                )
            }
            item {
                RelatedToSubjectSession(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    relatedSubject = state.relatedToSubject ?: "",
                    checkSecond = seconds,
                    selectedSubjectButtonClick = { isSubjectDropBottomSheet = true }
                )
            }
            item {
                ButtonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    cancelButtonClick = {
                        ServiceHelper.triggerForegroundService(
                            context = context,
                            action = ACTION_SERVICE_CANCEL
                        )
                    },
                    startButtonClick = {
                        if (state.subjectId != null && state.relatedToSubject != null) {
                            ServiceHelper.triggerForegroundService(
                                context = context,
                                action = if (currentTimerState == TimerState.STARTED) {
                                    ACTION_SERVICE_STOP
                                } else {
                                    ACTION_SERVICE_START
                                }
                            )
                            //Update subject id to avoid lose subject name when the app is destroyed and timer is run.
                            timerService.subjectId.value = state.subjectId
                        } else {
                            onEvent(SessionEvent.NotifyToUpdateSubject)
                        }
                    },
                    finishButtonClick = {
                        val duration = timerService.duration.toLong(DurationUnit.SECONDS)
                        if (duration >= 36) {
                            ServiceHelper.triggerForegroundService(
                                context = context,
                                action = ACTION_SERVICE_CANCEL
                            )
                        }
                        onEvent(SessionEvent.SaveSession(duration = duration))
                    },
                    timerState = currentTimerState,
                    seconds = seconds
                )
            }
            studySessionList(
                sectionTitle = "Study Session History",
                sessions = state.sessionList,
                emptyText = "You don't have any study session.\n start a study session to begin recording your progress",
                onDeleteIconClick = { session ->
                    isDeleteSessionDialogOpen = true
                    onEvent(SessionEvent.OnDeleteSessionButtonClick(session = session))
                }
            )
        }
    }
}

@Composable
private fun RelatedToSubjectSession(
    modifier: Modifier,
    relatedSubject: String,
    checkSecond: String,
    selectedSubjectButtonClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.task_date),
            style = MaterialTheme.typography.bodySmall
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                //Map long Date millis format to string
                text = relatedSubject,
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(
                onClick = {
                    selectedSubjectButtonClick()
                },
                enabled = checkSecond == "00"
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select task date"
                )
            }
        }
    }
}

@Composable
private fun ButtonSection(
    modifier: Modifier,
    timerState: TimerState,
    seconds: String,
    cancelButtonClick: () -> Unit,
    startButtonClick: () -> Unit,
    finishButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = cancelButtonClick,
            enabled = seconds != "00" && timerState != TimerState.STARTED
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Cancel"
            )
        }
        Button(
            onClick = startButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (timerState == TimerState.STARTED) Red
                else MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = when (timerState) {
                    TimerState.STARTED -> "Stop"
                    TimerState.STOPPED -> "Resume"
                    else -> "Start"
                }
            )
        }
        Button(
            onClick = finishButtonClick,
            enabled = seconds != "00" && timerState != TimerState.STARTED
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Finish"
            )
        }
    }
}