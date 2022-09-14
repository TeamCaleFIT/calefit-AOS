package com.example.calefit.ui.home.planner

import com.example.calefit.data.ExerciseList
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.ui.common.NestedRecyclerBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor() : NestedRecyclerBaseViewModel() {

    private val _todayDate = LocalDate.now()

    private val _exercisePlan = MutableStateFlow(DEFAULT_EXERCISE_LIST)
    val exercisePlan = _exercisePlan.asStateFlow()

    override fun addAdditionalExercise(exerciseList: List<ExerciseSelection>) {
        if (exerciseList.isEmpty()) {
            return
        }

        _exercisePlan.update { currentList ->
            val newList = currentList.list.toMutableList()
            exerciseList.forEach { selectedExercise ->
                newList.add(
                    ExerciseList.Exercise(
                        id = (newList.size + 1).toString(),
                        name = selectedExercise.name,
                    )
                )
            }
            currentList.copy(date = _todayDate.toString(), list = newList)
        }
    }

    override fun addAdditionalCycle(position: Int): Boolean {
        _exercisePlan.update { currentList ->
            val exerciseList = currentList.list.toMutableList()
            val targetExercise = exerciseList[position]
            val newCycleList = targetExercise.cycleList.toMutableList()
            newCycleList.add(
                ExerciseList.Sets(
                    id = (targetExercise.cycleList.size + 1).toString(),
                    cycle = DEFAULT_CYCLE_VALUE,
                    weight = DEFAULT_CYCLE_VALUE,
                )
            )
            val newExercise = targetExercise.copy(cycleList = newCycleList)
            exerciseList[position] = newExercise
            currentList.copy(list = exerciseList)
        }

        return true
    }

    override fun removeExercise(position: Int) {
        if (exercisePlan.value.list.isEmpty()) {
            return
        }

        _exercisePlan.update { currentList ->
            val newList = currentList.list.toMutableList()
            newList.removeAt(position)
            currentList.copy(list = newList)
        }
    }

    override fun removeCycle(position: Int): Boolean {
        if (exercisePlan.value.list[position].cycleList.size <= 1) {
            return false
        }

        _exercisePlan.update { currentList ->
            val exerciseList = currentList.list.toMutableList()
            val targetExercise = exerciseList[position]
            val newCycleList = targetExercise.cycleList.toMutableList()
            newCycleList.removeAt(newCycleList.size - 1)
            val newExercise = targetExercise.copy(cycleList = newCycleList)
            exerciseList[position] = newExercise
            currentList.copy(list = exerciseList)
        }

        return exercisePlan.value.list[position].cycleList.size > 1
    }

    fun setExerciseList(data: ExerciseList) {
        _exercisePlan.value = data
    }

    companion object {
        private val DEFAULT_EXERCISE_LIST = ExerciseList(list = listOf())
        private const val DEFAULT_CYCLE_VALUE = ""
    }
}
