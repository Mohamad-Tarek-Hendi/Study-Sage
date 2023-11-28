@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.studysage.feature_study_sage_app.presentation.subject.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun LargeTopAppBar(
    title: String?,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "NavigateIcon"
                )
            }
        },
        title = {
            Text(
                text = title ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = { onDeleteButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "DeleteIcon"
                )
            }
            IconButton(onClick = { onEditButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "EditIcon"
                )
            }
        }
    )
}