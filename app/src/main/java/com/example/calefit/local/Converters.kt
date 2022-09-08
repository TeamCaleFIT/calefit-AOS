package com.example.calefit.local

import androidx.room.TypeConverter
import com.example.calefit.data.ExerciseList
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun exerciseListToJson(value: List<ExerciseList.Exercise>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun exerciseJsonToModel(value: String): List<ExerciseList.Exercise> {
        return Json.decodeFromString(value)
    }
}