package com.example.calefit.ui.home.select

import androidx.lifecycle.ViewModel
import com.example.calefit.common.addElement
import com.example.calefit.common.removeElement
import com.example.calefit.data.ExerciseSelection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExerciseSelectViewModel @Inject constructor() : ViewModel() {

    private val _exerciseList = MutableStateFlow(DEFAULT_EXERCISE_LIST)
    val exerciseDataList = _exerciseList.asStateFlow()

    private val _selectedList = MutableStateFlow(DEFAULT_EXERCISE_LIST)
    val selectedExerciseList = _selectedList.asStateFlow()

    init {
        makeTemporarilyData()
    }

    private fun makeTemporarilyData() {
        val list = mutableListOf<ExerciseSelection>()
        list.add(ExerciseSelection("1", "스쿼트"))
        list.add(ExerciseSelection("2", "덤벨프레스"))
        list.add(ExerciseSelection("3", "벤치프레스"))
        list.add(ExerciseSelection("4", "렛풀다운"))
        list.add(ExerciseSelection("5", "풀업"))
        _exerciseList.value = list
    }

    fun addExercise(position: Int) {
        _selectedList.addElement(_exerciseList.value[position])
    }

    fun removeExercise(position: Int) {
        _selectedList.removeElement(_exerciseList.value[position])
    }

    companion object {
        private val DEFAULT_EXERCISE_LIST = listOf<ExerciseSelection>()
    }
}