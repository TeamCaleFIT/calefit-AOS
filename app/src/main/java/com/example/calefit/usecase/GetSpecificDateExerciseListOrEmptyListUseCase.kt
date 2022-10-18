package com.example.calefit.usecase

import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList
import com.example.calefit.repository.ExerciseRepository
import javax.inject.Inject

class GetSpecificDateExerciseListOrEmptyListUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(date: String): Aggregate<ExerciseList> {
        return when (val rawData = repository.getExerciseDataFromRepository()) {
            is Aggregate.Success -> {
                val result = rawData.data[date]
                if (result == null) {
                    Aggregate.Success(ExerciseList(list = emptyList()))
                } else {
                    Aggregate.Success(result)
                }
            }
            else -> {
                Aggregate.Error(IllegalArgumentException())
            }
        }
    }
}