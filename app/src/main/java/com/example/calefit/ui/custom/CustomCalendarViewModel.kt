package com.example.calefit.ui.custom

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.calefit.R
import com.example.calefit.data.CalendarDate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CustomCalendarViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _todayDate = LocalDate.now()

    private var _selectedDate = _todayDate

    private val _dayHeader = mutableListOf("일", "월", "화", "수", "목", "금", "토")

    private val _monthName = MutableStateFlow("")
    val monthName = _monthName.asStateFlow()

    private val _month = MutableStateFlow<List<CalendarDate>>(listOf())
    val month = _month.asStateFlow()

    private val _date = MutableStateFlow(_todayDate.toString())
    val date = _date.asStateFlow()

    init {
        makeCalendar()
    }

    fun changeDateBackground(position: Int) {
        makeCalendar()
        _month.update { list ->
            val newList = list.toMutableList()
            require(list[position] is CalendarDate.ItemDays)
            val data = list[position] as CalendarDate.ItemDays
            val newData = data.copy(isClicked = true)
            require(newData.isClicked)
            newList[position] = newData
            _date.value = newData.date
            newList
        }
    }

    fun getPreviousMonth() {
        _selectedDate = _selectedDate.minusMonths(1)
        makeCalendar()
    }

    fun getNextMonth() {
        _selectedDate = _selectedDate.plusMonths(1)
        makeCalendar()
    }

    private fun makeCalendar() {
        val list = mutableListOf<CalendarDate>()
        val dayList = makeDays()
        setMonthToString()

        _dayHeader.forEach { indicator ->
            list.add(CalendarDate.ItemHeader(
                dateIndicator = indicator
            ))
        }

        dayList.forEach { day ->
            list.add(day)
        }

        _month.value = list
    }

    private fun makeDays(): List<CalendarDate> {
        val selectedDate = _selectedDate
        val yearMonth = YearMonth.from(selectedDate)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        val selectedYear = selectedDate.year
        val selectedMonth = selectedDate.month.value
        val year = _todayDate.year
        val month = _todayDate.month.value
        val today = _todayDate.dayOfMonth
        val formatter = context.getString(R.string.year_month_day_format)

        val dayList = mutableListOf<CalendarDate>()

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                dayList.add(CalendarDate.ItemDays(
                    id = i,
                    day = "",
                ))
            } else {
                //TODO server data will be added in this section
                val day = (i - dayOfWeek).toString()

                // today year, month, day should be compared by the date of the calendar
                if (selectedYear == year
                    && selectedMonth == month
                    && day == today.toString()
                ) {
                    dayList.add(CalendarDate.ItemDays(
                        id = i,
                        day = day,
                        date = formatter.format(selectedYear, selectedMonth, day.toInt()),
                        isVisible = true,
                        isToday = true,
                    ))
                } else {
                    dayList.add(CalendarDate.ItemDays(
                        id = i,
                        day = day,
                        date = formatter.format(selectedYear, selectedMonth, day.toInt()),
                        isVisible = true,
                    ))
                }
            }
        }

        require(dayList.isNotEmpty())
        return dayList
    }

    private fun setMonthToString() {
        val formatter = DateTimeFormatter.ofPattern("MM월")
        _monthName.value = _selectedDate.format(formatter)
    }
}