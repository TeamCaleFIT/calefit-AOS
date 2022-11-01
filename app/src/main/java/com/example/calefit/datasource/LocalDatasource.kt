package com.example.calefit.datasource

import com.example.calefit.data.localdto.LocalExerciseListModel
import com.example.calefit.data.localdto.LocalExerciseTemplateModel
import kotlinx.coroutines.flow.Flow

interface LocalDatasource {

    fun getExerciseList(): Flow<List<LocalExerciseListModel>>

    fun getSpecificExerciseList(date: String): Flow<LocalExerciseListModel>

    fun getTemplate(): Flow<List<LocalExerciseTemplateModel>>

    suspend fun replaceExerciseList(data: LocalExerciseListModel)
}