package com.example.calefit.data

sealed class CalendarDate {

    data class ItemDays(
        val id: Int,
        val date: String,
        val onSchedule: Boolean = false,
        val isClicked: Boolean = false,
        val isVisible: Boolean = false,
        val isToday: Boolean = false,
    ) : CalendarDate()

    data class ItemHeader(
        val dateIndicator: String,
    ) : CalendarDate()
}