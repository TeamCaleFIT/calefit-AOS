package com.example.calefit.ui.home.planner

import com.example.calefit.data.ExerciseList
import com.example.calefit.ui.common.NestedRecyclerBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor() : NestedRecyclerBaseViewModel() {

    private val _todayDate = LocalDate.now()

    private val _exerciseList = MutableStateFlow(listOf<String>())

    val exercisePlan: MutableStateFlow<ExerciseList> = MutableStateFlow(DEFAULT_EXERCISE_LIST)

    override fun addAdditionalExercise() {
        TODO("Not yet implemented")
    }

    override fun addAdditionalCycle(position: Int) {
        TODO("Not yet implemented")
    }

    fun getExerciseName(exerciseList: List<String>) {
        _exerciseList.value = exerciseList
    }

    companion object {
        val DEFAULT_EXERCISE_LIST = ExerciseList(list = listOf())
    }
}