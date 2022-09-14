package com.example.calefit.usecase

import com.example.calefit.data.Aggregate
import com.example.calefit.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisePlannedDateListUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Aggregate<HashMap<String, Boolean>> {
        return when (val rawData = repository.getExerciseDataFromRepository()) {
            is Aggregate.Success -> {
                val resultMap: HashMap<String, Boolean> = hashMapOf()
                rawData.data.forEach { exerciseList ->
                    resultMap[exerciseList.value.date] = true
                }
                Aggregate.Success(resultMap)
            }
            else -> {
                Aggregate.Error(IllegalArgumentException())
            }
        }
    }
}
