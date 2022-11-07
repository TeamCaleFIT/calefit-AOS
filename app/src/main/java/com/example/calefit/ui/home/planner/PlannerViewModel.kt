package com.example.calefit.ui.home.planner

import androidx.lifecycle.viewModelScope
import com.example.calefit.data.DataLoadInfo
import com.example.calefit.data.ExerciseList
import com.example.calefit.ui.common.NestedRecyclerBaseViewModel
import com.example.calefit.usecase.GetExerciseListFromTemplateUseCase
import com.example.calefit.usecase.GetSpecificDateExerciseListOrEmptyListUseCase
import com.example.calefit.usecase.InsertExerciseListUseCase
import com.example.calefit.usecase.InsertExerciseTemplateListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val getSpecificDateExerciseListOrEmptyListUseCase: GetSpecificDateExerciseListOrEmptyListUseCase,
    private val getExerciseListFromTemplateUseCase: GetExerciseListFromTemplateUseCase,
    private val insertExerciseListUseCase: InsertExerciseListUseCase,
    private val insertExerciseTemplateListUseCase: InsertExerciseTemplateListUseCase,
) : NestedRecyclerBaseViewModel() {

    fun setData(data: DataLoadInfo) {
        super.dataLoadInfo = data

        if (data.templateName.isEmpty()) {
            setExerciseListByDate(data.initialClickedDate)
            return
        }

        setExerciseListByTemplate(data.templateName)
    }

    private fun setExerciseListByDate(date: String) {
        viewModelScope.launch {
            getSpecificDateExerciseListOrEmptyListUseCase(date).collect {
                if (it == null) {
                    super.exerciseLists.value = ExerciseList()
                    super.dataLoading.value = true
                    return@collect
                }

                super.exerciseLists.value = it
            }
        }
    }

    private fun setExerciseListByTemplate(templateName: String) {
        viewModelScope.launch {
            getExerciseListFromTemplateUseCase(templateName).collect {
                it?.let {
                    super.exerciseLists.value = it
                }
            }
        }
    }

    fun saveExerciseList() {
        viewModelScope.launch {
            insertExerciseListUseCase.save(
                exerciseLists.value,
                super.dataLoadInfo.initialClickedDate
            )
        }.onJoin
    }

    fun saveTemplateList(templateName: String?) {
        if (templateName == null) {
            return
        }

        viewModelScope.launch {
            insertExerciseTemplateListUseCase.save(
                exerciseLists.value,
                super.dataLoadInfo.initialClickedDate,
                templateName
            )
        }
    }

    companion object {
        private const val BLANK_PADDING = ' '
        private const val STATE_HANDLE_KEY = "initial_date"
    }
}