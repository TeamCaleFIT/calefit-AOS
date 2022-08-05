package com.example.calefit.data

sealed class CalendarDate {

    data class ItemDays(
        val date: String,
        val onSchedule: Boolean,
        val isClicked: Boolean = false,
    ) : CalendarDate()

    data class ItemHeader(
        val dateIndicator: String,
    ) : CalendarDate()
}