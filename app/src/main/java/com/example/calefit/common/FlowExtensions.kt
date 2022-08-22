package com.example.calefit.common

import kotlinx.coroutines.flow.MutableStateFlow

fun <E> MutableStateFlow<List<E>>.addElement(element: E) {
    if (element == null) {
        return
    }
    val tempMutableList = mutableListOf<E>()
    tempMutableList.addAll(this.value)
    tempMutableList.add(element)
    this.value = tempMutableList
}