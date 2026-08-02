package dev.enseor.ifccalendar.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.items

@Composable
fun YearView(
    selectedMonth: Int,
    selectedDay: Int?,
    isTodayYearDay: Boolean,
    isTodayLeapDay: Boolean,
    isLeapYear: Boolean,
    onMonthClick: (Int) -> Unit,
    onDayClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Months 1 to 6 (Jan to Jun)
        items(6) { index ->
            val monthIndex = index + 1
            MonthGrid(
                monthIndex = monthIndex,
                selectedDay = if (selectedMonth == monthIndex && selectedDay != 29) selectedDay else null,
                onDayClick = { day -> onDayClick(monthIndex, day) },
                isMini = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMonthClick(monthIndex) }
            )
        }

        // Leap Day (inserted between Jun and Sol only if it's a leap year)
        if (isLeapYear) {
            item {
                DayGrid(
                    title = "Leap Day",
                    isSelected = isTodayLeapDay,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Months 7 to 13 (Sol to Dec)
        items(7) { index ->
            val monthIndex = index + 7
            MonthGrid(
                monthIndex = monthIndex,
                selectedDay = if (selectedMonth == monthIndex && selectedDay != 29) selectedDay else null,
                onDayClick = { day -> onDayClick(monthIndex, day) },
                isMini = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMonthClick(monthIndex) }
            )
        }
        
        // Year Day (Positioned at the end)
        item {
            DayGrid(
                title = "Year Day",
                isSelected = isTodayYearDay,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
