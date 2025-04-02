package com.example.compose.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.compose.models.Event
import com.example.compose.viewmodels.EventsViewModel
import com.example.compose.viewmodels.EventsViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel = ViewModelProvider(
        context as androidx.lifecycle.ViewModelStoreOwner,
        EventsViewModelFactory()
    )[EventsViewModel::class.java]
    
    val events by viewModel.events.collectAsState()
    var showAddEventDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddEventDialog = true },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Event"
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(events) { event ->
                EventCard(
                    event = event,
                    onDelete = { viewModel.deleteEvent(event.id) }
                )
            }
        }

        if (showAddEventDialog) {
            AddEventScreen(
                onEventAdded = { name, date ->
                    viewModel.addEvent(name, date)
                    showAddEventDialog = false
                },
                onDismiss = { showAddEventDialog = false }
            )
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    onDelete: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(
                    onClick = onDelete,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete event"
                    )
                }
            }
            Text(
                text = dateFormat.format(event.date),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}