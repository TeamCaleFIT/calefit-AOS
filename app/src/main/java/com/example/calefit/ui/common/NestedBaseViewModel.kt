package com.example.calefit.ui.common

import androidx.lifecycle.ViewModel
import com.example.calefit.data.ExerciseSelection

abstract class NestedRecyclerBaseViewModel : ViewModel() {

    abstract fun addAdditionalExercise(exerciseList: List<ExerciseSelection>)

    abstract fun addAdditionalCycle(position: Int): Boolean

    abstract fun removeExercise(position: Int)

    abstract fun removeCycle(position: Int): Boolean

    abstract fun setCurrentAdapterPositions(userRecyclerviewClick: UserRecyclerviewClick)

    abstract fun setUserSelectedNumber(number: Int)
}
