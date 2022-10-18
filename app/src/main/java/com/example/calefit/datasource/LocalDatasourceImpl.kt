package com.example.calefit.datasource

import com.example.calefit.data.localdto.LocalExerciseListModel
import com.example.calefit.data.localdto.LocalExerciseTemplateModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatasourceImpl @Inject constructor() : LocalDatasource {
    override fun getExerciseList(): Flow<List<LocalExerciseListModel>> {
        TODO("Not yet implemented")
    }

    override fun getSpecificExerciseList(date: String): LocalExerciseListModel {
        TODO("Not yet implemented")
    }

    override fun getTemplate(): Flow<List<LocalExerciseTemplateModel>> {
        TODO("Not yet implemented")
    }
}