package com.example.calefit.datasource

import com.example.calefit.data.localdto.LocalExerciseListModel
import com.example.calefit.data.localdto.LocalExerciseTemplateModel
import com.example.calefit.local.ExerciseDao
import com.example.calefit.local.TemplateDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatasourceImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val templateDao: TemplateDao
) : LocalDatasource {
    override fun getExerciseList(): Flow<List<LocalExerciseListModel>> =
        exerciseDao.getAllExerciseList()

    override fun getSpecificExerciseList(date: String): Flow<LocalExerciseListModel> =
        exerciseDao.getSpecificExerciseList(date)

    override fun getTemplate(): Flow<List<LocalExerciseTemplateModel>> =
        templateDao.getAllTemplates()

    override suspend fun replaceExerciseList(data: LocalExerciseListModel) {
        exerciseDao.insertExercise(data)
    }
}