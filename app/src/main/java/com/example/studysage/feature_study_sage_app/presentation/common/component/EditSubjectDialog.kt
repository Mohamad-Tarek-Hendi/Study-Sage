package com.example.studysage.feature_study_sage_app.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.studysage.R
import com.example.studysage.feature_study_sage_app.domain.model.Subject

@Composable
fun EditSubjectDialog(
    isOpen: Boolean,
    selectedColor: List<Color>,
    subjectName: String,
    goalHour: String,
    onColorChange: (List<Color>) -> Unit,
    onSubjectNameValueChange: (String) -> Unit,
    onGoalHourValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmationClick: () -> Unit
) {
    //Check Error
    var subjectNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var goalHourError by rememberSaveable { mutableStateOf<String?>(null) }

    subjectNameError = when {
        subjectName.isBlank() -> "Please enter subject name."
        subjectName.length < 2 -> "Subject Name is too short."
        subjectName.length > 20 -> "Subject Name is too long."
        else -> null
    }

    goalHourError = when {
        goalHour.isBlank() -> "Please enter goal hour name."
        goalHour.toFloatOrNull() == null -> "Invalid number."
        goalHour.toFloat() < 1f -> "please set at least 1 hour."
        goalHour.toFloat() > 1000f -> "please set a maximum of 1000 hours."
        else -> null
    }

    if (isOpen) {
        AlertDialog(
            icon = {
                Icon(Icons.Filled.Add, contentDescription = "Example Icon")
            },
            title = {
                Text(text = stringResource(id = R.string.subject_hint_title))
            },
            text = {
                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Subject.subjectCardColors.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = 1.dp,
                                        color = if (color == selectedColor)
                                            Color.Black
                                        else
                                            Color.Transparent,

                                        shape = CircleShape
                                    )
                                    .background(brush = Brush.verticalGradient(color))
                                    .clickable {
                                        onColorChange(color)
                                    }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = subjectName,
                        onValueChange = onSubjectNameValueChange,
                        label = {
                            Text(text = stringResource(id = R.string.subject_name))
                        },
                        singleLine = true,
                        isError = subjectNameError != null && subjectName.isNotBlank(),
                        supportingText = { Text(text = subjectNameError.orEmpty()) }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = goalHour,
                        onValueChange = onGoalHourValueChange,
                        label = {
                            Text(text = stringResource(id = R.string.goal_study_hour))
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = goalHourError != null && goalHour.isNotBlank(),
                        supportingText = { Text(text = goalHourError.orEmpty()) }
                    )
                }

            },
            onDismissRequest = {
                onDismissRequest()
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Dismiss")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmationClick()
                    },
                    enabled = subjectNameError == null && goalHourError == null
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}