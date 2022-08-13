package com.example.calefit.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calefit.data.ExerciseList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _clickedDate = MutableStateFlow("")

    private val _exerciseList = MutableStateFlow(
        listOf(
            ExerciseList(
                date = "2022-08-09",
                list = listOf(
                    ExerciseList.Exercise(
                        id = "1",
                        name = "스쿼트",
                        cycle = "3회",
                        weight = "100kg"
                    ),
                    ExerciseList.Exercise(
                        id = "2",
                        name = "덤벨",
                        cycle = "10회",
                        weight = "40kg"
                    ),
                    ExerciseList.Exercise(
                        id = "3",
                        name = "벤치프레스",
                        cycle = "5회",
                        weight = "120kg"
                    )
                )
            ),
            ExerciseList(
                date = "2022-08-10",
                list = listOf(
                    ExerciseList.Exercise(
                        id = "1",
                        name = "런지",
                        cycle = "5회",
                        weight = "20kg"
                    )
                )
            )
        )
    )

    val exerciseList = MutableStateFlow<List<ExerciseList.Exercise>>(listOf())

    fun setDate(date: String) {
        _clickedDate.value = date
        require(date.isNotEmpty())
        val list = _exerciseList.value.find { it.date == date }
        if (list != null) {
            exerciseList.value = list.list
        } else {
            exerciseList.value = listOf(
                ExerciseList.Exercise(
                    id = "1",
                    name = "",
                    cycle = "",
                    weight = "",
                )
            )
        }
    }
}