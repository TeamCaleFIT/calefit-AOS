package com.example.calefit.repository

import com.example.calefit.data.ExerciseList
import com.example.calefit.data.localdto.LocalExerciseListModel
import com.example.calefit.data.localdto.LocalExerciseTemplateModel
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    //TODO method getter from server

    fun getExerciseDataFromRoom(): Flow<HashMap<String, ExerciseList>>

    fun getExerciseTemplateFromRoom(): Flow<HashMap<String, ExerciseList>>

    suspend fun insertExerciseList(value: LocalExerciseListModel)

    suspend fun insertExerciseTemplateList(value: LocalExerciseTemplateModel)
}
