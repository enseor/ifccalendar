package dev.enseor.ifccalendar.ui

import androidx.lifecycle.ViewModel
import dev.enseor.ifccalendar.logic.IfcDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

enum class CalendarViewMode {
    YEAR, MONTH
}

class CalendarViewModel : ViewModel() {
    private val _currentGregorianDate = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    val currentGregorianDate: StateFlow<LocalDate> = _currentGregorianDate.asStateFlow()

    private val _currentIfcDate = MutableStateFlow(IfcDate.fromGregorian(_currentGregorianDate.value))
    val currentIfcDate: StateFlow<IfcDate> = _currentIfcDate.asStateFlow()

    private val _selectedMonth = MutableStateFlow(currentIfcDate.value.month)
    val selectedMonth: StateFlow<Int> = _selectedMonth.asStateFlow()

    private val _viewMode = MutableStateFlow(CalendarViewMode.YEAR)
    val viewMode: StateFlow<CalendarViewMode> = _viewMode.asStateFlow()

    private fun getCurrentIfcDate(): IfcDate {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        return IfcDate.fromGregorian(now)
    }

    fun selectMonth(month: Int) {
        if (month in 1..13) {
            _selectedMonth.value = month
            _viewMode.value = CalendarViewMode.MONTH
        }
    }

    fun setViewMode(mode: CalendarViewMode) {
        _viewMode.value = mode
    }
}
