package com.example.calefit.data.remotedto

data class CalendarDateListDTO(
    val date: String?,
    val list: List<UserDateList>?
) {
    data class UserDateList(
        val id: String?,
        val name: String?,
        val cycle: String?,
        val weight: String?,
    )
}