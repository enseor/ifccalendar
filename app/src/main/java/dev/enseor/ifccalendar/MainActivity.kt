package dev.enseor.ifccalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.enseor.ifccalendar.logic.IfcDate
import dev.enseor.ifccalendar.ui.CalendarViewMode
import dev.enseor.ifccalendar.ui.CalendarViewModel
import dev.enseor.ifccalendar.ui.components.MonthGrid
import dev.enseor.ifccalendar.ui.components.YearView
import dev.enseor.ifccalendar.ui.theme.IFCTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IFCTheme {
                val viewModel: CalendarViewModel = viewModel()
                val currentIfcDate by viewModel.currentIfcDate.collectAsState()
                val currentGregorianDate by viewModel.currentGregorianDate.collectAsState()
                val selectedMonth by viewModel.selectedMonth.collectAsState()
                val viewMode by viewModel.viewMode.collectAsState()
                var selectedDay by remember { mutableStateOf<Int?>(null) }

                BackHandler(enabled = viewMode == CalendarViewMode.MONTH) {
                    viewModel.setViewMode(CalendarViewMode.YEAR)
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    if (viewMode == CalendarViewMode.YEAR) "IFC Calendar - Year ${currentIfcDate.year}" else "IFC Calendar",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                if (viewMode == CalendarViewMode.MONTH) {
                                    IconButton(onClick = { viewModel.setViewMode(CalendarViewMode.YEAR) }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back to Year",
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (viewMode == CalendarViewMode.YEAR) {
                            Card(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Today is: ${currentGregorianDate.dayOfMonth} ${currentGregorianDate.month}, ${currentGregorianDate.year}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Text(
                                        text = "IFC: ${currentIfcDate.day} ${currentIfcDate.monthName}, ${currentIfcDate.year}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            YearView(
                                selectedMonth = selectedMonth,
                                selectedDay = selectedDay,
                                onMonthClick = { month ->
                                    viewModel.selectMonth(month)
                                },
                                onDayClick = { month, day ->
                                    viewModel.selectMonth(month)
                                    selectedDay = day
                                }
                            )
                        } else {
                            MonthSelector(
                                currentMonth = selectedMonth,
                                onMonthChange = viewModel::selectMonth
                            )

                            MonthGrid(
                                monthIndex = selectedMonth,
                                selectedDay = selectedDay,
                                onDayClick = { selectedDay = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            if (selectedDay != null) {
                                Card(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                ) {
                                    val monthName = IfcDate.monthNames[selectedMonth - 1]
                                    Text(
                                        text = "Selected: Day $selectedDay of $monthName",
                                        modifier = Modifier.padding(16.dp),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MonthSelector(
    currentMonth: Int,
    onMonthChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { if (currentMonth > 1) onMonthChange(currentMonth - 1) }) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Previous Month")
        }

        Text(
            text = IfcDate.monthNames[currentMonth - 1],
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        IconButton(onClick = { if (currentMonth < 13) onMonthChange(currentMonth + 1) }) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next Month")
        }
    }
}
