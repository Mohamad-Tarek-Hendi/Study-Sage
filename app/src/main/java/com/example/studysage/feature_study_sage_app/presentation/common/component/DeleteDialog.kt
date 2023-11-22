package com.example.studysage.feature_study_sage_app.presentation.common.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.studysage.R

@Composable
fun DeleteDialog(
    isOpen: Boolean,
    deleteMessage: String,
    onDismissRequest: () -> Unit,
    onConfirmationClick: () -> Unit
) {

    if (isOpen) {
        AlertDialog(
            icon = {
                Icon(Icons.Filled.Delete, contentDescription = "Example Icon")
            },
            title = {
                Text(text = stringResource(id = R.string.delete_dialog_title))
            },
            text = {
                Text(text = deleteMessage)
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
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}