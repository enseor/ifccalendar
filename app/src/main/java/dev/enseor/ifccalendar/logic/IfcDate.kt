package dev.enseor.ifccalendar.logic

import kotlinx.datetime.*

data class IfcDate(
    val year: Int,
    val month: Int, // 1 to 13
    val day: Int,   // 1 to 28
    val isYearDay: Boolean = false,
    val isLeapDay: Boolean = false
) {
    val monthName: String
        get() = when (month) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "Sol"
            8 -> "July"
            9 -> "August"
            10 -> "September"
            11 -> "October"
            12 -> "November"
            13 -> "December"
            else -> "Unknown"
        }

    companion object {
        val monthNames = listOf(
            "January", "February", "March", "April", "May", "June",
            "Sol", "July", "August", "September", "October", "November", "December"
        )

        fun fromGregorian(date: LocalDate): IfcDate {
            val dayOfYear = date.dayOfYear
            val isLeap = date.year % 4 == 0 && (date.year % 100 != 0 || date.year % 400 == 0)

            // Year Day is the last day of the year (365th or 366th)
            if (dayOfYear == (if (isLeap) 366 else 365)) {
                return IfcDate(date.year, 13, 29, isYearDay = true)
            }

            var adjustedDayOfYear = dayOfYear
            
            // Handle Leap Day (placed after June 28, which is day 168)
            if (isLeap) {
                if (dayOfYear == 169) {
                    return IfcDate(date.year, 6, 29, isLeapDay = true)
                } else if (dayOfYear > 169) {
                    adjustedDayOfYear--
                }
            }

            val month = (adjustedDayOfYear - 1) / 28 + 1
            val day = (adjustedDayOfYear - 1) % 28 + 1

            return IfcDate(date.year, month, day)
        }
    }
}
