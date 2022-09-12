package com.example.calefit.ui.home.detali

import com.example.calefit.data.ExerciseSelection
import com.example.calefit.ui.common.NestedRecyclerBaseViewModel
import com.example.calefit.ui.common.InputCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor() : NestedRecyclerBaseViewModel() {
    override fun addAdditionalExercise(exerciseList: List<ExerciseSelection>) {
        TODO("Not yet implemented")
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

    override fun getUserInputValue(
        outerPosition: Int,
        innerPosition: Int,
        value: String?,
        category: InputCategory
    ) {
        TODO("Not yet implemented")
    }


}