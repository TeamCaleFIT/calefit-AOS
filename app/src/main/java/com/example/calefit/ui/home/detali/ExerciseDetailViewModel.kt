package com.example.calefit.ui.home.detali

import android.util.Log
import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.ui.common.NestedRecyclerBaseViewModel
import com.example.calefit.data.UserRecyclerviewClick
import com.example.calefit.ui.home.planner.PlannerViewModel
import com.example.calefit.usecase.GetSpecificDateExerciseListOrEmptyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getSpecificDateExerciseListOrEmptyListUseCase: GetSpecificDateExerciseListOrEmptyListUseCase
) : NestedRecyclerBaseViewModel() {

    val dataLoading = MutableStateFlow(false)

    fun setExerciseByDate(date: String) {
        when (val data = getSpecificDateExerciseListOrEmptyListUseCase(date)) {
            is Aggregate.Success -> {
                dataLoading.value = false
                super._exercisePlan.value = data.data
            }
            is Aggregate.Error -> {
                dataLoading.value = false
            }
            is Aggregate.Loading -> {
                dataLoading.value = true
            }
        }
    }
}