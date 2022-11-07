package com.example.calefit.ui.home.detali

import androidx.lifecycle.viewModelScope
import com.example.calefit.data.ExerciseList
import com.example.calefit.ui.common.NestedRecyclerBaseViewModel
import com.example.calefit.usecase.GetExerciseListFromTemplateUseCase
import com.example.calefit.usecase.InsertExerciseTemplateListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getExerciseListFromTemplateUseCase: GetExerciseListFromTemplateUseCase,
    private val insertExerciseTemplateListUseCase: InsertExerciseTemplateListUseCase
) : NestedRecyclerBaseViewModel() {

    fun setExerciseByTemplateName(templateName: String) {
        viewModelScope.launch {
            getExerciseListFromTemplateUseCase(templateName).collect {
                if (it == null) {
                    super.exerciseLists.value = ExerciseList()
                    dataLoading.value = true
                    return@collect
                }

                super.exerciseLists.value = it
            }
        }
    }

    fun saveTemplate() {
        viewModelScope.launch {
            insertExerciseTemplateListUseCase.save(
                exerciseLists.value,
                exerciseLists.value.date,
                exerciseLists.value.templateName
            )
        }.onJoin
    }
}