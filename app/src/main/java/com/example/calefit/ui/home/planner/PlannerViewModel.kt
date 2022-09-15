package com.example.calefit.ui.home.planner

import android.util.Log
import com.example.calefit.data.ExerciseList
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.ui.common.InputCategory
import com.example.calefit.ui.common.NestedRecyclerBaseViewModel
import com.example.calefit.ui.common.UserRecyclerviewClick
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

    private lateinit var _userInputExercisePlan: ExerciseList

    private lateinit var _userInput: UserRecyclerviewClick

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
            currentList.copy(list = newList)
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

        Log.d("PlannerViewModel", position.toString())

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

    override fun setCurrentAdapterPositions(userClickPosition: UserRecyclerviewClick) {
        _userInput = userClickPosition
    }

    override fun setUserSelectedNumber(number: Int) {
        if (number == 0) {
            return
        }

        _exercisePlan.update { currentList ->
            val exerciseList = currentList.list.toMutableList()
            val exercise = exerciseList[_userInput.outerPosition]
            val setList = exercise.cycleList.toMutableList()
            val set = setList[_userInput.innerPosition]
            val newSet = when (_userInput.category) {
                InputCategory.CYCLE -> {
                    set.copy(cycle = number)
                }
                InputCategory.WEIGHT -> {
                    set.copy(weight = number)
                }
            }
            setList[_userInput.innerPosition] = newSet
            val newExercise = exercise.copy(cycleList = setList)
            exerciseList[_userInput.outerPosition] = newExercise
            currentList.copy(list = exerciseList)
        }

        Log.d(
            "PlannerViewModel",
            "cycle : ${_exercisePlan.value.list[_userInput.outerPosition].cycleList[_userInput.innerPosition].cycle}   " +
                    "weight : ${_exercisePlan.value.list[_userInput.outerPosition].cycleList[_userInput.innerPosition].weight}"
        )
    }

    companion object {
        private val DEFAULT_EXERCISE_LIST = ExerciseList(list = listOf())
        private const val DEFAULT_CYCLE_VALUE = 0
    }
}