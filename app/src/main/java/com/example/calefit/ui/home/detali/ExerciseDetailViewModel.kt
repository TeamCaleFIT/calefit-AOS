package com.example.calefit.ui.home.detali

import android.util.Log
import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList
import com.example.calefit.data.ExerciseSelection
import com.example.calefit.ui.common.NestedRecyclerBaseViewModel
import com.example.calefit.data.UserRecyclerviewClick
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

    private val _exerciseList = MutableStateFlow(ExerciseList(list = emptyList()))
    val exerciseList = _exerciseList.asStateFlow()

    val dataLoading = MutableStateFlow(false)

    override fun addAdditionalExercise(selectedExerciseList: List<ExerciseSelection>) {
        if (selectedExerciseList.isEmpty()) {
            return
        }

        _exerciseList.update { currentList ->
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

    override fun addAdditionalCycle(position: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeExercise(position: Int) {
        TODO("Not yet implemented")
    }

    override fun removeCycle(position: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun setCurrentAdapterPositions(userRecyclerviewClick: UserRecyclerviewClick) {
        TODO("Not yet implemented")
    }

    override fun setUserSelectedNumber(number: Int) {
        TODO("Not yet implemented")
    }

    fun setExerciseByDate(date: String) {
        when (val data = getSpecificDateExerciseListOrEmptyListUseCase(date)) {
            is Aggregate.Success -> {
                dataLoading.value = false
                _exerciseList.value = data.data
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