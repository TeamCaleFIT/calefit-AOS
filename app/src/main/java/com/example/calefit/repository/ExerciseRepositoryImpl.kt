package com.example.calefit.repository

import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList
import com.example.calefit.datasource.RemoteDatasource
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val datasource: RemoteDatasource,
) : ExerciseRepository {

    private val _exerciseHashMap = hashMapOf<String, ExerciseList>()

    private val exerciseList = listOf(
        ExerciseList(
            date = "2022-08-09",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "스쿼트",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "3", "30")
                    ),
                ),
                ExerciseList.Exercise(
                    id = "2",
                    name = "덤벨",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "2", "100")
                    )
                ),
                ExerciseList.Exercise(
                    id = "3",
                    name = "벤치프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "5", "400")
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
                        ExerciseList.Sets("1", "3", "320")
                    )
                )
            )
        ),
        ExerciseList(
            date = "2022-08-30",
            list = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "벤치프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "3", "200"),
                        ExerciseList.Sets("2", "5", "220")
                    )
                ),
                ExerciseList.Exercise(
                    id = "2",
                    name = "덤벨프레스",
                    cycleList = listOf(
                        ExerciseList.Sets("1", "2", "180"),
                        ExerciseList.Sets("2", "1", "120")
                    )
                )
            )
        )
    )

    //TODO when use server this code will changed to call datasource
    override fun getExerciseDataFromRepository(): Aggregate<HashMap<String, ExerciseList>> {

        //TODO return Aggregate Fail if datasource has no data from the datasource

        if (_exerciseHashMap.isNotEmpty()) {
            return Aggregate.Success(_exerciseHashMap)
        }

        val map: HashMap<String, ExerciseList> = hashMapOf()
        exerciseList.forEach {
            map[it.date] = it
        }

        return Aggregate.Success(map)
    }


}
