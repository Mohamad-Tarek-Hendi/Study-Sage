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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.studysage.R
import com.example.studysage.core.presentation.theme.Red
import com.example.studysage.feature_study_sage_app.presentation.common.component.DatePickerDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.DeleteDialog
import com.example.studysage.feature_study_sage_app.presentation.common.component.SubjectDropBottomSheet
import com.example.studysage.feature_study_sage_app.presentation.common.mapping.changeMillisToDateString
import com.example.studysage.feature_study_sage_app.presentation.common.util.Priority
import com.example.studysage.feature_study_sage_app.presentation.task.component.TaskPriority
import com.example.studysage.feature_study_sage_app.presentation.task.component.TaskTopAppBar
import kotlinx.coroutines.launch
import java.time.Instant

@Composable
fun TaskScreen() {

    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }

    var titleError by rememberSaveable { mutableStateOf<String?>(null) }
    titleError = when {
        title.isBlank() -> stringResource(id = R.string.task_title_is_blank_error)
        title.length < 4 -> stringResource(id = R.string.task_title_is_too_short_error)
        title.length > 30 -> stringResource(id = R.string.task_title_is_too_long_error)
        else -> null
    }

    var descriptionError by rememberSaveable { mutableStateOf<String?>(null) }
    descriptionError = when {
        description.isBlank() -> stringResource(id = R.string.task_description_is_blank_error)
        description.length < 4 -> stringResource(id = R.string.task_title_is_too_short_error)
        description.length > 30 -> stringResource(id = R.string.task_description_is_too_long_error)
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

    DeleteDialog(
        isOpen = isOpenDeleteDialog,
        deleteMessage = stringResource(R.string.delete_task),
        onDismissRequest = { isOpenDeleteDialog = false },
        onConfirmationClick = { isOpenDeleteDialog = false }
    )

    DatePickerDialog(
        datePickerState = datePickerState,
        isOpen = isOpenDatePickerDialog,
        onDismissRequest = { isOpenDatePickerDialog = false },
        onConfirmButtonClick = { isOpenDatePickerDialog = false },
        onDateSelected = { date_selected ->

        }
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

    Scaffold(topBar = {
        TaskTopAppBar(title = "Task",
            isTaskExist = true,
            isComplete = false,
            checkBoxBorderColor = Red,
            onBackButtonClick = { /*TODO*/ },
            onDeleteButtonClick = { isOpenDeleteDialog = true }) {}
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
                .verticalScroll(state = rememberScrollState())
        ) {

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
                label = {
                    Text(text = stringResource(id = R.string.task_title))
                },
                singleLine = true,
                isError = titleError != null && title.isNotBlank(),
                supportingText = { Text(text = titleError.orEmpty()) })
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { description = it },
                label = {
                    Text(text = stringResource(id = R.string.task_description))
                },
                isError = descriptionError != null && description.isNotBlank(),
                supportingText = { Text(text = descriptionError.orEmpty()) })
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
                    text = datePickerState.selectedDateMillis.changeMillisToDateString(),
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
                    modifier = Modifier.weight(1f)
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
                Text(
                    text = "English",
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
                onClick = { /*TODO*/ },
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
) {
    Priority.entries.forEach { priority ->
        TaskPriority(modifier = modifier,
            priorityTitle = priority.title,
            backgroundColor = priority.color,
            borderColor = if (priority == Priority.AVERAGE) {
                Color.White
            } else {
                Color.Transparent
            },
            priorityTitleColor = if (priority == Priority.AVERAGE) {
                Color.White
            } else {
                Color.White.copy(alpha = 0.7f)
            },
            onClick = { /*TODO*/ })
    }
}
