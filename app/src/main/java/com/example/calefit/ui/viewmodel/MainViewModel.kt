package com.example.calefit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseList
import com.example.calefit.usecase.GetExerciseListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getExerciseListUseCase: GetExerciseListUseCase
) : ViewModel() {

    private val _clickedDate = MutableStateFlow("")
    val clickedDate = _clickedDate.asStateFlow()

    private val _exerciseList = MutableStateFlow(
        getExerciseListUseCase()
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
        _clickedDate.value = date
        require(date.isNotEmpty())
    }
}