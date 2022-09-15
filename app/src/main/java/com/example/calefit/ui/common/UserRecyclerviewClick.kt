package com.example.calefit.ui.common

data class UserRecyclerviewClick(
    val outerPosition: Int = 0,
    val innerPosition: Int = 0,
    val category: InputCategory = InputCategory.CYCLE
)