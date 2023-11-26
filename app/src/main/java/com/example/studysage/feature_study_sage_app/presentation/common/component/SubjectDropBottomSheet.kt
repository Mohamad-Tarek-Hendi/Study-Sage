package com.example.studysage.feature_study_sage_app.presentation.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.studysage.R
import com.example.studysage.feature_study_sage_app.domain.model.Subject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDropBottomSheet(
    sheetState: SheetState,
    isOpen: Boolean,
    subjectList: List<Subject>,
    onSubjectClick: (Subject) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (isOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onDismissRequest() },
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(text = "Related To Subject")
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                }
            }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(subjectList) { subject ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSubjectClick(subject)
                            }
                    ) {
                        Text(
                            text = subject.name ?: ""
                        )
                    }
                }
                if (subjectList.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.no_subject_in_bottom_sheet)
                        )
                    }
                }
            }
        }
    }
}