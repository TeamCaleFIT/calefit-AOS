package com.example.calefit.ui.home.main

import androidx.lifecycle.ViewModel
import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList
import com.example.calefit.usecase.GetExerciseDetailUseCase
import com.example.calefit.usecase.GetSpecificDateExerciseListOrNullUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getExerciseDetailUseCase: GetExerciseDetailUseCase,
    private val getSpecificDateExerciseListOrNullUseCase: GetSpecificDateExerciseListOrNullUseCase
) : ViewModel() {

    private val _clickedDate = MutableStateFlow("")
    val clickedDate = _clickedDate.asStateFlow()

    private val _exerciseList = MutableStateFlow(
        getExerciseDetailUseCase()
    )

    val exerciseMap = _exerciseList.map {
        when (it) {
            is Aggregate.Success -> {
                dataLoading.value = false
                it.data
            }
            is Aggregate.Error -> {
                dataLoading.value = false
                emptyMap()
            }
            is Aggregate.Loading -> {
                dataLoading.value = true
                emptyMap()
            }
        }
    }

    val dataLoading = MutableStateFlow(false)

    fun setDate(date: String) {
        require(date.isNotEmpty())
        _clickedDate.value = date
    }

    fun getClickedExerciseListOrNull(): ExerciseList? {
        return when (val data = getSpecificDateExerciseListOrNullUseCase(clickedDate.value)) {
            is Aggregate.Success -> {
                dataLoading.value = false
                data.data
            }
            is Aggregate.Error -> {
                dataLoading.value = false
                null
            }
            is Aggregate.Loading -> {
                dataLoading.value = true
                null
            }
        }
    }
}
