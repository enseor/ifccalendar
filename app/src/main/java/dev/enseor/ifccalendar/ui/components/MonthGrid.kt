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
            monthName = monthName,
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
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp)),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        shape = RoundedCornerShape(8.dp)
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
                dayNames.forEach { dayName ->
                    Text(
                        text = dayName,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.secondary,
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
    monthName: String,
    dayNames: List<String>,
    selectedDay: Int?,
    onDayClick: (Int) -> Unit
) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = monthName,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Start
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            dayNames.forEach { dayName ->
                Text(
                    text = dayName,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.secondary,
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

