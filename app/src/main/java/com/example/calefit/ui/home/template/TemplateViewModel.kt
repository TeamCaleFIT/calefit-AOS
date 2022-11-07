package com.example.calefit.ui.home.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calefit.data.DataLoadInfo
import com.example.calefit.data.ExerciseTemplateSummary
import com.example.calefit.usecase.GetExerciseTemplateListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val getExerciseTemplateListUseCase: GetExerciseTemplateListUseCase
) : ViewModel() {

    private val _templateSummaryList: MutableStateFlow<List<ExerciseTemplateSummary>> =
        MutableStateFlow(listOf())

    private var dataLoadInfo = DataLoadInfo()

    val templateSummaryList = _templateSummaryList.asStateFlow()

    val dataLoading = MutableStateFlow(false)

    init {
        getTemplateDataFromRepository()
    }

    fun selectTemplate(position: Int) {
        _templateSummaryList.update { currentList ->
            val newList = mutableListOf<ExerciseTemplateSummary>()
            currentList.forEachIndexed { index, summary ->
                if (index == position && !currentList[index].isClicked) {
                    newList.add(summary.copy(isClicked = true))
                    dataLoadInfo =
                        dataLoadInfo.copy(templateName = currentList[index].templateTitle)
                } else {
                    newList.add(summary.copy(isClicked = false))
                }
            }
            newList
        }
    }

    fun setDataLoadInfo(data: DataLoadInfo) {
        this.dataLoadInfo = data
    }

    fun getLoadDataInfo() = dataLoadInfo

    private fun getTemplateDataFromRepository() {
        viewModelScope.launch {
            getExerciseTemplateListUseCase().collect {
                if (it.isEmpty()) {
                    _templateSummaryList.value = emptyList()
                    dataLoading.value = true
                    return@collect
                }
                _templateSummaryList.value = it
            }
        }
    }
}