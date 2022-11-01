package com.example.calefit.usecase

import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseDailyDetail
import com.example.calefit.repository.ExerciseRepository
import javax.inject.Inject

class GetExerciseDetailUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Aggregate<HashMap<String, List<ExerciseDailyDetail>>> {
        return when (val rawData = repository.getExerciseDataFromRoom()) {
            is Aggregate.Success -> {
                val map: HashMap<String, List<ExerciseDailyDetail>> = hashMapOf()
                rawData.data.forEach {
                    val detailList = mutableListOf<ExerciseDailyDetail>()
                    it.value.list.forEachIndexed { index, exercise ->
                        val cycleCount = exercise.cycleList.size
                        var totalWeight = 0
                        exercise.cycleList.forEach { sets ->
                            totalWeight += (sets.weight.toInt() * sets.cycle.toInt())
                        }
                        detailList.add(
                            ExerciseDailyDetail(
                                id = index.toString(),
                                name = exercise.name,
                                cycleCount = cycleCount.toString(),
                                weightVolume = totalWeight.toString(),
                            )
                        )
                    }
                    map[it.key] = detailList
                }
                Aggregate.Success(map)
            }
            else -> {
                Aggregate.Error(IllegalArgumentException())
            }
        }
    }
}
