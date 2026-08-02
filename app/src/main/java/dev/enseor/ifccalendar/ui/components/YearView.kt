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
        items(13) { index ->
            val monthIndex = index + 1
            MonthGrid(
                monthIndex = monthIndex,
                selectedDay = if (selectedMonth == monthIndex) selectedDay else null,
                onDayClick = { day -> onDayClick(monthIndex, day) },
                isMini = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMonthClick(monthIndex) }
            )
        }
    }
}
