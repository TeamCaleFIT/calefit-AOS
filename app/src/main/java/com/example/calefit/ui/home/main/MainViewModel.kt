package com.example.calefit.ui.home.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calefit.data.ExerciseDailyDetail
import com.example.calefit.usecase.GetExerciseDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getExerciseDetailUseCase: GetExerciseDetailUseCase,
) : ViewModel() {

    init {
        getData()
    }

    private val _clickedDate = MutableStateFlow("")
    val clickedDate = _clickedDate.asStateFlow()

    private val _exerciseList = MutableStateFlow(
        hashMapOf<String, List<ExerciseDailyDetail>>()
    )

    val exerciseMap = _exerciseList.asStateFlow()

    val dataLoading = MutableStateFlow(false)

    fun setDate(date: String) {
        require(date.isNotEmpty())
        _clickedDate.value = date
        Log.d("MainViewModel", "date : ${_clickedDate.value}")
    }

    private fun getData() {
        viewModelScope.launch {
            getExerciseDetailUseCase().collect {
                _exerciseList.value = it
            }
        }
    }
}
