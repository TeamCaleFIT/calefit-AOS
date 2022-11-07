package com.example.calefit.usecase

import com.example.calefit.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExercisePlannedDateListUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Flow<HashMap<String, Boolean>> {
        return repository.getExerciseDataFromRoom().map { exerciseList ->
            val resultMap: HashMap<String, Boolean> = hashMapOf()
            exerciseList.forEach {
                resultMap[it.key] = true
            }
            resultMap
        }
    }
}