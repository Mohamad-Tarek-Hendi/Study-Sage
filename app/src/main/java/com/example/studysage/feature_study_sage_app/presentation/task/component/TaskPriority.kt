package com.example.studysage.feature_study_sage_app.presentation.task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskPriority(
    modifier: Modifier = Modifier,
    priorityTitle: String,
    backgroundColor: Color,
    borderColor: Color,
    priorityTitleColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor)
            .clickable { onClick() }
            .clip(shape = RoundedCornerShape(5.dp))
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = priorityTitle,
            color = priorityTitleColor
        )
    }
}