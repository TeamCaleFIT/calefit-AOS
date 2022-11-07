package com.example.calefit.ui.common

import androidx.lifecycle.ViewModel
import com.example.calefit.data.DataLoadInfo
import com.example.calefit.data.ExerciseList
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.data.UserRecyclerviewClick
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class NestedRecyclerBaseViewModel : ViewModel() {

    protected val exerciseLists: MutableStateFlow<ExerciseList> = MutableStateFlow(
        ExerciseList()
    )
    val exercisePlan = exerciseLists.asStateFlow()

    val dataLoading = MutableStateFlow(false)

    private lateinit var _userInput: UserRecyclerviewClick

    protected var selectedDate = MutableStateFlow("")

    protected var dataLoadInfo = DataLoadInfo()

    fun addAdditionalExercise(selectedExerciseList: List<ExerciseSelection>) {
        if (selectedExerciseList.isEmpty()) {
            return
        }

        exerciseLists.update { currentList ->
            val newList = currentList.list.toMutableList()
            selectedExerciseList.forEach { selectedExercise ->
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

    fun addAdditionalCycle(position: Int): Boolean {
        exerciseLists.update { currentList ->
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

    fun removeExercise(position: Int) {
        if (exercisePlan.value.list.isEmpty()) {
            return
        }

        exerciseLists.update { currentList ->
            val newList = currentList.list.toMutableList()
            newList.removeAt(position)
            currentList.copy(list = newList)
        }
    }

    fun removeCycle(position: Int): Boolean {
        if (exercisePlan.value.list[position].cycleList.size <= 1) {
            return false
        }

        exerciseLists.update { currentList ->
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

    fun setCurrentAdapterPositions(userClickPosition: UserRecyclerviewClick) {
        _userInput = userClickPosition
    }

    fun setUserSelectedNumber(number: Int) {
        if (number == 0) {
            return
        }

        exerciseLists.update { currentList ->
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
    }

    fun getLoadDataInfo() = dataLoadInfo

    companion object {
        private const val DEFAULT_CYCLE_VALUE = 0
    }
}
