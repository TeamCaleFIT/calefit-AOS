package com.example.calefit.ui.home.planner

import android.util.Log
import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.ui.common.InputCategory
import com.example.calefit.ui.common.NestedRecyclerBaseViewModel
import com.example.calefit.data.UserRecyclerviewClick
import com.example.calefit.usecase.GetSpecificDateExerciseListOrEmptyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val getSpecificDateExerciseListOrEmptyListUseCase: GetSpecificDateExerciseListOrEmptyListUseCase
) : NestedRecyclerBaseViewModel() {

    fun setExerciseList(date: String) {
        if (date.isEmpty()) {
            return
        }

        when (val data = getSpecificDateExerciseListOrEmptyListUseCase(date)) {
            is Aggregate.Success -> {
                _exercisePlan.value = data.data
            }
            else -> {
                _exercisePlan.value = DEFAULT_EXERCISE_LIST
            }
        }
    }

    companion object {
        private val DEFAULT_EXERCISE_LIST = ExerciseList(list = listOf())
    }
}