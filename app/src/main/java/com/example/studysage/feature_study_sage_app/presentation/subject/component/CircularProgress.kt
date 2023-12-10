package com.example.studysage.feature_study_sage_app.presentation.subject.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.studysage.core.presentation.theme.Blue
import com.example.studysage.core.presentation.theme.md_theme_dark_onPrimaryContainer

@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    progressPercentageValue: Int,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) progress else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        ), label = ""
    )

    //Trigger animPlayed to true to start animation
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        //To draw you own shapes
        Canvas(modifier = Modifier.size(75.dp)) {
            val sweepAngle = 360 * curPercentage.value
            val remainingAngle = 360 - sweepAngle

            // Draw the remaining arc with the surfaceVariant color
            drawArc(
                color = md_theme_dark_onPrimaryContainer,
                -90f + sweepAngle,
                remainingAngle,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            drawArc(
                color = Blue,
                -90f,
                sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

        }
        Text(
            text = "$progressPercentageValue%"
        )
    }
}