package com.example.calefit.data

import java.io.Serializable

sealed class CalendarDate {

    data class ItemDays(
        val id: Int,
        val day: String,
        val date: String = "",
        val hasSchedule: Boolean = false,
        val isClicked: Boolean = false,
        val isVisible: Boolean = false,
        val isToday: Boolean = false,
    ) : CalendarDate(), Serializable

    data class ItemHeader(
        val dateIndicator: String,
    ) : CalendarDate()
}