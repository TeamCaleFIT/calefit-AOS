package com.example.calefit.data

import com.example.calefit.ui.common.InputCategory

data class UserRecyclerviewClick(
    val outerPosition: Int = 0,
    val innerPosition: Int = 0,
    val category: InputCategory = InputCategory.CYCLE
)