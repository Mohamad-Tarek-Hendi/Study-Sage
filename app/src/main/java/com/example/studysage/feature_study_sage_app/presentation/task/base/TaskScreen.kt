@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.studysage.feature_study_sage_app.presentation.task.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studysage.R
import com.example.studysage.feature_study_sage_app.presentation.common.component.DatePickerDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.DeleteDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.SubjectDropBottomSheet
import com.example.studysage.feature_study_sage_app.presentation.common.converter.changeMillisToDateString
import com.example.studysage.feature_study_sage_app.presentation.common.util.Priority
import com.example.studysage.feature_study_sage_app.presentation.common.util.SnackBarEvent
import com.example.studysage.feature_study_sage_app.presentation.task.TaskEvent
import com.example.studysage.feature_study_sage_app.presentation.task.TaskState
import com.example.studysage.feature_study_sage_app.presentation.task.TaskViewModel
import com.example.studysage.feature_study_sage_app.presentation.task.component.TaskPriority
import com.example.studysage.feature_study_sage_app.presentation.task.component.TaskTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant

data class TaskScreenNavArgs(
    val subjectId: Int?,
    val taskId: Int?
)

@Destination(navArgsDelegate = TaskScreenNavArgs::class)
@Composable
fun TaskScreenRoute(
    navigator: DestinationsNavigator
) {
    val viewModel: TaskViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    TaskScreen(
        state = state,
        onEvent = onEvent,
        snackBarEvent = viewModel.snackBarEventFlow,
        onBackButtonClick = {
            navigator.navigateUp()
        }
    )
}

@Composable
private fun TaskScreen(
    state: TaskState,
    snackBarEvent: SharedFlow<SnackBarEvent>,
    onEvent: (TaskEvent) -> Unit,
    onBackButtonClick: () -> Unit
) {

    val scope = rememberCoroutineScope()

    var titleError by rememberSaveable { mutableStateOf<String?>(null) }
    titleError = when {
        state.title.isBlank() -> stringResource(id = R.string.task_title_is_blank_error)
        state.title.length < 4 -> stringResource(id = R.string.task_title_is_too_short_error)
        state.title.length > 30 -> stringResource(id = R.string.task_title_is_too_long_error)
        else -> null
    }

    var descriptionError by rememberSaveable { mutableStateOf<String?>(null) }
    descriptionError = when {
        state.description.isBlank() -> stringResource(id = R.string.task_description_is_blank_error)
        state.description.length < 4 -> stringResource(id = R.string.task_title_is_too_short_error)
        state.description.length > 30 -> stringResource(id = R.string.task_description_is_too_long_error)
        else -> null
    }

    val datePickerState = rememberDatePickerState(
        //set initial value from today date
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    val modalBottomSheetState = rememberModalBottomSheetState()

    var isOpenDeleteDialog by remember { mutableStateOf(false) }

    var isOpenDatePickerDialog by remember { mutableStateOf(false) }

    var isSubjectDropBottomSheet by remember { mutableStateOf(false) }

    val snackBarState = remember { SnackbarHostState() }

    DeleteDialog(
        isOpen = isOpenDeleteDialog,
        deleteMessage = stringResource(R.string.delete_task),
        onDismissRequest = {
            isOpenDeleteDialog = false
        },
        onConfirmationClick = {
            onEvent(TaskEvent.DeleteTask)
            isOpenDeleteDialog = false
        }
    )

    DatePickerDialog(
        datePickerState = datePickerState,
        isOpen = isOpenDatePickerDialog,
        onDismissRequest = { isOpenDatePickerDialog = false },
        onConfirmButtonClick = {
            isOpenDatePickerDialog = false
        },
        onDateSelected = { date_selected ->
            onEvent(TaskEvent.OnDateChange(dateSelected = date_selected))
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
                onEvent(
                    TaskEvent.OnRelatedSubjectSelect(subject = subject)
                )
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

                SnackBarEvent.NavigateUp -> {
                    onBackButtonClick()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarState
            )
        },
        topBar = {
            TaskTopAppBar(title = "Task",
                isTaskExist = state.taskId != null,
                isComplete = state.isTaskIsComplete,
                checkBoxBorderColor = state.priority.color,
                onBackButtonClick = {
                    onBackButtonClick()
                },
                onDeleteButtonClick = {
                    isOpenDeleteDialog = true
                },
                onCheckBoxClick = {
                    onEvent(TaskEvent.OnIsCompleteChange)
                })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
                .verticalScroll(state = rememberScrollState())
        ) {

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = state.title,
                onValueChange = {
                    onEvent(TaskEvent.OnTitleChange(title = it))
                },
                label = {
                    Text(text = stringResource(id = R.string.task_title))
                },
                singleLine = true,
                isError = titleError != null && state.title.isNotBlank(),
                supportingText = {
                    Text(text = titleError.orEmpty())
                })
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.description,
                onValueChange = {
                    onEvent(TaskEvent.OnDescriptionChange(description = it))
                },
                label = {
                    Text(text = stringResource(id = R.string.task_description))
                },
                isError = descriptionError != null && state.description.isNotBlank(),
                supportingText = {
                    Text(text = descriptionError.orEmpty())
                })
            Spacer(modifier = Modifier.height(20.dp))
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
                    text = state.date.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = {
                    isOpenDatePickerDialog = true
                }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select task date"
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.task_priority),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                PrioritySection(
                    modifier = Modifier.weight(1f),
                    state = state,
                    onPriorityClick = { priority ->
                        onEvent(TaskEvent.OnPriorityChange(priority = priority))
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.related_subject),
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val firstSubject = state.subjectList.firstOrNull()?.name ?: ""
                Text(
                    text = state.relatedTaskToSubject ?: firstSubject,
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = {
                    isSubjectDropBottomSheet = true
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select subject"
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    onEvent(TaskEvent.SaveTask)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                enabled = titleError == null && descriptionError == null
            ) {
                Text(
                    text = stringResource(id = R.string.save_task)
                )
            }
        }
    }
}

@Composable
fun PrioritySection(
    modifier: Modifier,
    state: TaskState,
    onPriorityClick: (Priority) -> Unit
) {
    Priority.entries.forEach { priority ->
        TaskPriority(modifier = modifier,
            priorityTitle = priority.title,
            backgroundColor = priority.color,
            borderColor = if (priority == state.priority) {
                Color.White
            } else {
                Color.Transparent
            },
            priorityTitleColor = if (priority == state.priority) {
                Color.White
            } else {
                Color.White.copy(alpha = 0.7f)
            },
            onClick = {
                onPriorityClick(priority)
            })
    }
}
