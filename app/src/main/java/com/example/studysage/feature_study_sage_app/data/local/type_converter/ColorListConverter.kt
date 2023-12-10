package com.example.studysage.feature_study_sage_app.data.local.type_converter

import androidx.room.TypeConverter

class ColorListConverter {
    @TypeConverter
    fun fromColorList(colorList: List<Int>?): String {
        return colorList?.joinToString(",") { it.toString() } ?: ""
    }

    @TypeConverter
    fun toColorList(colorListString: String?): List<Int> {
        return colorListString?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
    }
}