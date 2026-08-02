package dev.enseor.ifccalendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.clip

import androidx.compose.runtime.remember

import androidx.compose.foundation.shape.CircleShape

@Composable
fun MonthGrid(
    monthIndex: Int,
    selectedDay: Int?,
    onDayClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isMini: Boolean = false
) {
    val dayNames = remember(isMini) {
        if (isMini) {
            listOf("S", "M", "T", "W", "T", "F", "S")
        } else {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        }
    }
    val monthName = remember(monthIndex) {
        IfcDate.monthNames.getOrNull(monthIndex - 1) ?: "Month $monthIndex"
    }

    if (isMini) {
        MonthMiniContainer(
            modifier = modifier,
            monthName = monthName,
            dayNames = dayNames,
            selectedDay = selectedDay,
            onDayClick = onDayClick
        )
    } else {
        MonthFullView(
            modifier = modifier,
            dayNames = dayNames,
            selectedDay = selectedDay,
            onDayClick = onDayClick
        )
    }
}

@Composable
private fun MonthMiniContainer(
    modifier: Modifier,
    monthName: String,
    dayNames: List<String>,
    selectedDay: Int?,
    onDayClick: (Int) -> Unit
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp)),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(
                text = monthName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                textAlign = TextAlign.Center
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                dayNames.forEachIndexed { index, dayName ->
                    val isWeekend = index == 0 || index == 6
                    Text(
                        text = dayName,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isWeekend) MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 7.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))
            MonthDaysMini(selectedDay, onDayClick)
        }
    }
}

@Composable
private fun MonthFullView(
    modifier: Modifier,
    dayNames: List<String>,
    selectedDay: Int?,
    onDayClick: (Int) -> Unit
) {
    Column(modifier = modifier.padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            dayNames.forEachIndexed { index, dayName ->
                val isWeekend = index == 0 || index == 6
                Text(
                    text = dayName,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = if (isWeekend) MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.height(300.dp)
        ) {
            items(28) { index ->
                val day = index + 1
                val isSelected = day == selectedDay
                val isWeekend = index % 7 == 0 || index % 7 == 6

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.2f),
                            shape = CircleShape
                        )
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) 
                            else if (isWeekend) Color.Gray.copy(alpha = 0.05f) 
                            else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable { onDayClick(day) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = when {
                                isSelected -> MaterialTheme.colorScheme.primary
                                isWeekend -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                                else -> MaterialTheme.colorScheme.onSurface
                            },
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
                        )
                    )
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
                    val isSelected = remember(selectedDay) { day == selectedDay }
                    val isWeekend = col == 0 || col == 6
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(1.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) 
                                else if (isWeekend) Color.Gray.copy(alpha = 0.1f)
                                else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { onDayClick(day) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.sp,
                                color = when {
                                    isSelected -> MaterialTheme.colorScheme.primary
                                    isWeekend -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                                    else -> MaterialTheme.colorScheme.onSurface
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

