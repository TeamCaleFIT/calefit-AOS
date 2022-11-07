package com.example.calefit.usecase

import com.example.calefit.data.ExerciseList
import com.example.calefit.data.localdto.LocalExerciseTemplateModel
import com.example.calefit.repository.ExerciseRepository
import javax.inject.Inject

class InsertExerciseTemplateListUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend fun save(data: ExerciseList, date: String, templateName: String) {
        repository.insertExerciseTemplateList(
            LocalExerciseTemplateModel.from(
                data,
                date,
                templateName
            )
        )
    }
}