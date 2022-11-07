package com.example.calefit.repository

import com.example.calefit.data.ExerciseList
import com.example.calefit.data.localdto.LocalExerciseListModel
import com.example.calefit.data.localdto.LocalExerciseTemplateModel
import com.example.calefit.datasource.LocalDatasource
import com.example.calefit.datasource.RemoteDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val localDatasource: LocalDatasource
) : ExerciseRepository {

    //TODO when use server this code will changed to call datasource
    override fun getExerciseDataFromRoom(): Flow<HashMap<String, ExerciseList>> {
        return localDatasource.getExerciseList().map { modelList ->
            val exerciseHashList = HashMap<String, ExerciseList>()
            modelList.forEach { localExerciseListModel ->
                exerciseHashList[localExerciseListModel.date] = ExerciseList(
                    date = localExerciseListModel.date,
                    templateName = localExerciseListModel.templateName,
                    list = localExerciseListModel.list
                )
            }
            exerciseHashList
        }
    }

    override fun getExerciseTemplateFromRoom(): Flow<HashMap<String, ExerciseList>> {
        return localDatasource.getTemplate().map { list ->
            val exerciseTemplateList = hashMapOf<String, ExerciseList>()
            list.forEach { templateModel ->
                exerciseTemplateList[templateModel.templateName] = ExerciseList(
                    date = templateModel.date,
                    templateName = templateModel.templateName,
                    list = templateModel.list
                )
            }
            exerciseTemplateList
        }
    }

    override suspend fun insertExerciseList(value: LocalExerciseListModel) {
        localDatasource.insertExerciseList(value)
    }

    override suspend fun insertExerciseTemplateList(value: LocalExerciseTemplateModel) {
        localDatasource.insertExerciseTemplate(value)
    }
}
