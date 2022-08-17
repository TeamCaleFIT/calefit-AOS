package com.example.calefit.usecase

import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList
import com.example.calefit.repository.ExerciseRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class GetExerciseListUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {

    operator fun invoke(): Aggregate<HashMap<String, List<ExerciseList.Exercise>>> {
        return when (val data = exerciseRepository.getExerciseListOrError()) {
            is Aggregate.Success -> {
                Aggregate.Success(data.data)
            }
            else -> {
                Aggregate.Error(IllegalArgumentException())
            }
        }
    }
}