package com.example.studysage.feature_study_sage_app.presentation.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studysage.R
import com.example.studysage.feature_study_sage_app.domain.model.Task
import com.example.studysage.feature_study_sage_app.presentation.common.converter.changeMillisToDateString
import com.example.studysage.feature_study_sage_app.presentation.common.util.Priority

fun LazyListScope.taskList(
    sectionTitle: String,
    tasks: List<Task>,
    emptyText: String,
    onTaskCardClick: (Int) -> Unit,
    onCheckBoxClick: (Task) -> Unit

) {
    item {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp)
        )
    }

    if (tasks.isEmpty()) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.task),
                    contentDescription = emptyText,
                    contentScale = ContentScale.Crop
                )
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
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
    }
    items(tasks) { task ->
        TaskCard(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            task = task,
            onClick = {
                onTaskCardClick(task.id ?: 0)
            },
            onTaskCheckBoxClick = {
                onCheckBoxClick(task)
            }
        )
    }
}

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    onClick: () -> Unit,
    onTaskCheckBoxClick: () -> Unit,
) {
    ElevatedCard(
        modifier = modifier
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TaskCheckBox(
                isTaskComplete = task.isTaskComplete ?: false,
                borderColor = Priority.fromInt(task.priority ?: 0).color,
                onCheckBoxClick = {
                    onTaskCheckBoxClick()
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = task.title ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration =
                    if (task.isTaskComplete == true)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.date.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}