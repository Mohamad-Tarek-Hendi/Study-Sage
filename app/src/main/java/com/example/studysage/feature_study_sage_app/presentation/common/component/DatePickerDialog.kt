@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.studysage.feature_study_sage_app.presentation.common.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.studysage.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DatePickerDialog(
    datePickerState: DatePickerState,
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    if (isOpen) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(it)
                        }
                        onConfirmButtonClick()
                    }
                ) {
                    Text(text = stringResource(R.string.confirm_date_dialog))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(text = stringResource(R.string.delete_date_dialog))
                }
            },
            content = {
                DatePicker(
                    state = datePickerState,
                    dateValidator = { timesTamp ->
                        val selectedDate = Instant
                            .ofEpochMilli(timesTamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val currentDate = LocalDate.now(ZoneId.systemDefault())
                        selectedDate >= currentDate
                    }
                )
            }
        )
    }

}