package com.example.calefit.repository

import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList
import com.example.calefit.datasource.RemoteDatasource
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val datasource: RemoteDatasource,
) : ExerciseRepository {

    private val exerciseList = listOf(
        ExerciseList(
            date = "2022-08-09",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "스쿼트",
                    cycleList = listOf(
                        ExerciseList.Cycle("1", "3", "30")
                    ),
                ),
                ExerciseList.Exercise(
                    id = "2",
                    name = "덤벨",
                    cycleList = listOf(
                        ExerciseList.Cycle("1", "2", "100")
                    )
                ),
                ExerciseList.Exercise(
                    id = "3",
                    name = "벤치프레스",
                    cycleList = listOf(
                        ExerciseList.Cycle("1", "5", "400")
                    )
                )
            )
        ),
        ExerciseList(
            date = "2022-08-10",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "런지",
                    cycleList = listOf(
                        ExerciseList.Cycle("1", "3", "320")
                    )
                )
            )
        )
    )



    override fun getExerciseListOrError(): Aggregate<HashMap<String, List<ExerciseList.Exercise>>> {
        return if (exerciseList.isNotEmpty()) {
//            datasource.getDateList()
            val map = hashMapOf<String, List<ExerciseList.Exercise>>()
            exerciseList.forEach {
                map[it.date] = it.list
            }
            Aggregate.Success(map)
        } else {
            Aggregate.Error(IllegalArgumentException())
        }
    }
}