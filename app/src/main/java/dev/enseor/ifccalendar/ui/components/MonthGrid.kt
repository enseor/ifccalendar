package dev.enseor.ifccalendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.enseor.ifccalendar.logic.IfcDate

@Composable
fun MonthGrid(
    monthIndex: Int,
    selectedDay: Int?,
    onDayClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isMini: Boolean = false
) {
    val dayNames = if (isMini) {
        listOf("S", "M", "T", "W", "T", "F", "S")
    } else {
        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    }
    val monthName = IfcDate.monthNames.getOrNull(monthIndex - 1) ?: "Month $monthIndex"

    Column(modifier = modifier.padding(if (isMini) 4.dp else 8.dp)) {
        // Month Title
        Text(
            text = monthName,
            style = if (isMini) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = if (isMini) 4.dp else 16.dp),
            textAlign = if (isMini) TextAlign.Center else TextAlign.Start
        )

        // Day Names Header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            dayNames.forEach { dayName ->
                Text(
                    text = dayName,
                    modifier = Modifier.weight(1f),
                    style = (if (isMini) MaterialTheme.typography.labelSmall else MaterialTheme.typography.labelMedium).copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(if (isMini) 4.dp else 8.dp))

        // Days Grid
        val gridModifier = if (isMini) {
            Modifier.fillMaxWidth()
        } else {
            Modifier.height(300.dp)
        }

        // Use a simple Column/Row structure if isMini to avoid nested LazyVerticalGrids
        if (isMini) {
            MonthDaysMini(selectedDay, onDayClick)
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = gridModifier
            ) {
                items(28) { index ->
                    val day = index + 1
                    val isSelected = day == selectedDay

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f)
                            )
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
                            )
                            .clickable { onDayClick(day) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MonthDaysMini(
    selectedDay: Int?,
    onDayClick: (Int) -> Unit
) {
    Column {
        for (row in 0 until 4) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until 7) {
                    val day = row * 7 + col + 1
                    val isSelected = day == selectedDay
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(1.dp)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent
                            )
                            .clickable { onDayClick(day) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }
        }
    }
}

