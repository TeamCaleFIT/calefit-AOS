package com.example.calefit.ui.home.template

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseTemplateSummary
import com.example.calefit.usecase.GetExerciseTemplateListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val getExerciseTemplateListUseCase: GetExerciseTemplateListUseCase
) : ViewModel() {

    private val _templateSummaryList: MutableStateFlow<List<ExerciseTemplateSummary>> =
        MutableStateFlow(listOf())

    val templateSummaryList = _templateSummaryList.asStateFlow()

    val dataLoading = MutableStateFlow(false)

    private var _selectedDate = ""

    init {
        getTemplateDataFromRepository()
    }

    private lateinit var _selectedTemplate: ExerciseTemplateSummary

    fun selectTemplate(position: Int) {
        _selectedTemplate = _templateSummaryList.value[position]

        _templateSummaryList.update { currentList ->
            val newList = mutableListOf<ExerciseTemplateSummary>()
            currentList.forEachIndexed { index, summary ->
                if (index == position && !currentList[index].isClicked) {
                    newList.add(summary.copy(isClicked = true))
                    _selectedDate = currentList[index].exerciseDate
                } else {
                    newList.add(summary.copy(isClicked = false))
                }
            }
            newList
        }
    }

    fun getSelectedDate() = _selectedDate

    private fun getTemplateDataFromRepository() {
        when (val data = getExerciseTemplateListUseCase()) {
            is Aggregate.Success -> {
                dataLoading.value = false
                _templateSummaryList.value = data.data
            }
            is Aggregate.Error -> {
                dataLoading.value = false
                _templateSummaryList.value = emptyList()
            }
            is Aggregate.Loading -> {
                dataLoading.value = true
                _templateSummaryList.value = emptyList()
            }
        }
    }
}