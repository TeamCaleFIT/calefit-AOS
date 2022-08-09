package com.example.calefit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _clickedDate = MutableStateFlow("")

    fun setDate(date: String) {
        _clickedDate.value = date
        check(date.isNotEmpty())
    }
}