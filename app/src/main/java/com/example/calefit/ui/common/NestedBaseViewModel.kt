package com.example.calefit.ui.common

import androidx.lifecycle.ViewModel

abstract class NestedRecyclerBaseViewModel : ViewModel() {

    abstract fun addAdditionalExercise()

    abstract fun addAdditionalCycle(position: Int)
}