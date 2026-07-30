package dev.enseor.ifccalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import dev.enseor.ifccalendar.ui.CalendarViewModel
import dev.enseor.ifccalendar.ui.components.MonthGrid
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
                val selectedMonth by viewModel.selectedMonth.collectAsState()
                var selectedDay by remember { mutableStateOf<Int?>(null) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "IFC Calendar",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
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
                        Text(
                            text = "Today is: ${currentIfcDate.day} ${currentIfcDate.monthName}, ${currentIfcDate.year}",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )

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
