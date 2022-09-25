package com.example.calefit.data

import java.util.*

data class ExerciseTemplateSummary(
    val id: UUID,
    val templateTitle: String,
    val exerciseCount: Int,
    val exerciseDate: String,
    var isClicked: Boolean = false
)