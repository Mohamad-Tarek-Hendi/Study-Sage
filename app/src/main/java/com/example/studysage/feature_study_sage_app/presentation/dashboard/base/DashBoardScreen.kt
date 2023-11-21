package com.example.studysage.feature_study_sage_app.presentation.dashboard.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studysage.R
import com.example.studysage.feature_study_sage_app.domain.model.StudySession
import com.example.studysage.feature_study_sage_app.domain.model.Subject
import com.example.studysage.feature_study_sage_app.domain.model.Task
import com.example.studysage.feature_study_sage_app.presentation.common.component.PerformanceCard
import com.example.studysage.feature_study_sage_app.presentation.common.component.TopAppBar
import com.example.studysage.feature_study_sage_app.presentation.common.component.studySessionList
import com.example.studysage.feature_study_sage_app.presentation.common.component.taskList
import com.example.studysage.feature_study_sage_app.presentation.common.data.PerformanceCardItem
import com.example.studysage.feature_study_sage_app.presentation.dashboard.component.AddSubjectDialog
import com.example.studysage.feature_study_sage_app.presentation.dashboard.component.SubjectCard

@Composable
fun DashBoardScreen() {

    //Fake Data
    val subjectList =
        listOf(
            Subject(
                id = 0,
                name = "English",
                goalHours = 10f,
                color = Subject.subjectCardColors[0]
            ),
            Subject(
                id = 0,
                name = "Math",
                goalHours = 10f,
                color = Subject.subjectCardColors[1]
            ),
            Subject(
                id = 0,
                name = "Arabic",
                goalHours = 10f,
                color = Subject.subjectCardColors[2]
            ),
            Subject(
                id = 0,
                name = "Science",
                goalHours = 10f,
                color = Subject.subjectCardColors[3]
            ),
            Subject(
                id = 0,
                name = "History",
                goalHours = 10f,
                color = Subject.subjectCardColors[4]
            )
        )

    val taskList =
        listOf(
            Task(
                id = 1,
                taskSubjectId = 0,
                title = "Prepare first 5 pages",
                description = "",
                date = 0L,
                priority = 0,
                relatedTaskToSubject = "",
                isTaskComplete = false
            ),
            Task(
                id = 1,
                taskSubjectId = 0,
                title = "Prepare second 5 pages",
                description = "",
                date = 0L,
                priority = 1,
                relatedTaskToSubject = "",
                isTaskComplete = true
            )
        )

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

    var isAddSubjectDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedColor by remember {
        mutableStateOf(Subject.subjectCardColors.random())
    }

    var subjectName by remember {
        mutableStateOf("")
    }

    var goalHour by remember {
        mutableStateOf("")
    }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        selectedColor = selectedColor,
        subjectName = subjectName,
        goalHour = goalHour,
        onColorChange = { selectedColor = it },
        onSubjectNameValueChange = { subjectName = it },
        onGoalHourValueChange = { goalHour = it },
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmationClick = { isAddSubjectDialogOpen = false }
    )

    Scaffold(
        topBar =
        {
            TopAppBar()
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                PerformanceCardSection(
                    performanceCardsItems = listOf(
                        PerformanceCardItem(
                            name = "Subject Count",
                            count = "5"
                        ),
                        PerformanceCardItem(
                            name = "Studied Hours",
                            count = "10"
                        ),
                        PerformanceCardItem(
                            name = "Goal Study Hours",
                            count = "15"
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.subject),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    IconButton(onClick = {
                        isAddSubjectDialogOpen = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Subject"
                        )
                    }
                }

                SubjectCardSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subjectList,
                    emptyText = stringResource(id = R.string.hint_to_add_subject)
                )

            }
            item {
                FilledTonalButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text(text = stringResource(id = R.string.start_study_session))
                }
            }
            taskList(
                sectionTitle = "UpComing Task ",
                tasks = taskList,
                emptyText = "You don't have any task.\n Click the + button to add new task",
                onTaskCardClick = {},
                onCheckBoxClick = {/*TODO*/ }
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            studySessionList(
                sectionTitle = "Recent Session Study",
                sessions = studySessionList,
                emptyText = "You don't have any study session.\n start a study session to begin recording your progress",
                onDeleteIconClick = {/*TODO*/ }
            )

        }

    }

}


@Composable
fun PerformanceCardSection(
    modifier: Modifier,
    performanceCardsItems: List<PerformanceCardItem>
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(performanceCardsItems.size) { index ->
            PerformanceCard(
                headText = performanceCardsItems[index].name,
                countText = performanceCardsItems[index].count,
            )
        }
    }
}

@Composable
fun SubjectCardSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyText: String
) {

    if (subjectList.isEmpty()) {
        Column(modifier = modifier) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.empty_data),
                contentDescription = emptyText,
                contentScale = ContentScale.FillBounds
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

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(start = 2.dp, end = 2.dp)
    ) {
        items(subjectList) { subject ->
            SubjectCard(
                subjectName = subject.name,
                gradient = subject.color,
                onClick = {

                }
            )
        }
    }
}