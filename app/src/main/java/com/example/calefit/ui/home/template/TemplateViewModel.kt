package com.example.calefit.ui.home.template

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calefit.data.Aggregate
import com.example.calefit.data.ExerciseTemplateSummary
import com.example.calefit.usecase.GetExerciseTemplateListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    getExerciseTemplateListUseCase: GetExerciseTemplateListUseCase
) : ViewModel() {

    private val _templateSummaryList = MutableStateFlow(
        getExerciseTemplateListUseCase()
    )

    val templateSummaryList = _templateSummaryList.map {
        when (it) {
            is Aggregate.Success -> {
                dataLoading.value = false
                it.data
            }
            is Aggregate.Error -> {
                dataLoading.value = false
                emptyList()
            }
            is Aggregate.Loading -> {
                dataLoading.value = true
                emptyList()
            }
        }
    }

    val dataLoading = MutableStateFlow(false)

    private lateinit var _selectedTemplate: ExerciseTemplateSummary

    fun selectTemplate(position: Int) {
        viewModelScope.launch {
            templateSummaryList.collect {
                if (it.isNotEmpty()) {
                    _selectedTemplate = it[position]
                }
            }
        }
    }
}