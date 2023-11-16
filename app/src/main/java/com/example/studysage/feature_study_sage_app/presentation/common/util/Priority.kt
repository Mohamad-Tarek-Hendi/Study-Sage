package com.example.studysage.feature_study_sage_app.presentation.common.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import com.example.studysage.core.presentation.theme.Green
import com.example.studysage.core.presentation.theme.Red

enum class Priority(
    val title: String,
    val color: Color,
    val value: Int
) {
    LOW(title = "Low", color = Green, value = 0),
    AVERAGE(title = "Average", color = Yellow, value = 1),
    HIGH(title = "High", color = Red, value = 2);

    companion object {
        /*Utility function
        *This function takes an Int value and returns the corresponding Priority instance(LOW, AVERAGE, HIGH)
        * It provides a convenient way to retrieve a Priority value based on an integer input.
        * Here is an example of how you would use the fromInt function:
        * val priorityFromInt = Priority.fromInt(1)
        println(priorityFromInt.title) // Output: "Average"
        * */
        fun fromInt(value: Int) = values().firstOrNull() { it.value == value } ?: AVERAGE
    }
}