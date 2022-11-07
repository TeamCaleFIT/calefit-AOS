package com.example.calefit.usecase

import com.example.calefit.data.ExerciseDailyDetail
import com.example.calefit.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExerciseDetailUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Flow<HashMap<String, List<ExerciseDailyDetail>>> {
        return repository.getExerciseDataFromRoom().map { rawData ->
            val map: HashMap<String, List<ExerciseDailyDetail>> = hashMapOf()
            rawData.forEach {
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
            map
        }
    }
}