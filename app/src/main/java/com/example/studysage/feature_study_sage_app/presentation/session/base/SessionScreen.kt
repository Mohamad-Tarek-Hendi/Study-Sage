package com.example.studysage.feature_study_sage_app.presentation.session.base

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.studysage.R
import com.example.studysage.feature_study_sage_app.domain.model.StudySession
import com.example.studysage.feature_study_sage_app.presentation.common.component.DeleteDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.SubjectDropBottomSheet
import com.example.studysage.feature_study_sage_app.presentation.common.component.studySessionList
import com.example.studysage.feature_study_sage_app.presentation.session.component.SessionTopAppBar
import com.example.studysage.feature_study_sage_app.presentation.session.component.TimerSession
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(
) {
    val studySessionList =
        listOf(
            StudySession(
                id = 0,
                studySessionToSubject = 0,
                relatedStudySessionToSubject = "English",
                date = 2,
                duration = 0L
            ),
            StudySession(
                id = 0,
                studySessionToSubject = 0,
                relatedStudySessionToSubject = "Math",
                date = 2,
                duration = 0L
            )
        )

    val scope = rememberCoroutineScope()

    val modalBottomSheetState = rememberModalBottomSheetState()

    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    var isSubjectDropBottomSheet by remember { mutableStateOf(false) }

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        deleteMessage = stringResource(id = R.string.delete_session_message_in_session_screen),
        onConfirmationClick = { isDeleteSessionDialogOpen = false },
        onDismissRequest = { isDeleteSessionDialogOpen = false }
    )

    SubjectDropBottomSheet(
        sheetState = modalBottomSheetState,
        isOpen = isSubjectDropBottomSheet,
        subjectList = emptyList(),
        onSubjectClick = {
            scope.launch {
                modalBottomSheetState.hide()
            }.invokeOnCompletion {
                if (!modalBottomSheetState.isVisible) {
                    isSubjectDropBottomSheet = false
                }
            }
        },
        onDismissRequest = { isSubjectDropBottomSheet = false }
    )

    Scaffold(
        topBar = {
            SessionTopAppBar(
                title = stringResource(R.string.session_top_appbar_title),
                onBackButtonClick = {

                }
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
                        .aspectRatio(1f)
                )
            }
            item {
                RelatedToSubjectSession(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    relatedSubject = "English",
                    selectedSubjectButtonClick = { isSubjectDropBottomSheet = true }
                )
            }
            item {
                ButtonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    cancelButtonClick = { /*TODO*/ },
                    startButtonClick = { /*TODO*/ },
                    finishButtonClick = {}
                )
            }
            studySessionList(
                sectionTitle = "Study Session History",
                sessions = studySessionList,
                emptyText = "You don't have any study session.\n start a study session to begin recording your progress",
                onDeleteIconClick = { isDeleteSessionDialogOpen = true }
            )
        }
    }
}

@Composable
private fun RelatedToSubjectSession(
    modifier: Modifier,
    relatedSubject: String,
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
            IconButton(onClick = {
                selectedSubjectButtonClick()
            }) {
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
    cancelButtonClick: () -> Unit,
    startButtonClick: () -> Unit,
    finishButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { cancelButtonClick() }) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Cancel"
            )
        }
        Button(
            onClick = { startButtonClick() }) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Start"
            )
        }
        Button(
            onClick = { finishButtonClick() }) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Finish"
            )
        }
    }
}