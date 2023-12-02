package com.example.studysage.feature_study_sage_app.data.type_converter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter

class ColorConverter {
    @TypeConverter
    fun fromColor(color: Color): Int {
        // Convert the Color object to an integer representation
        return color.toArgb()
    }

    @TypeConverter
    fun toColor(colorInt: Int): Color {
        // Convert the integer representation back to a Color object
        return Color(colorInt)
    }
}