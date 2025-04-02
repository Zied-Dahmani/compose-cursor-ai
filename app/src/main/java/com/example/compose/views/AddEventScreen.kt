package com.example.compose.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun AddEventScreen(
    onEventAdded: (String, Date) -> Unit,
    onDismiss: () -> Unit
) {
    var eventName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(Date()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(
                "Add New Event",
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = eventName,
                    onValueChange = { eventName = it },
                    label = { Text("Event Name") },
                    modifier = Modifier.fillMaxWidth(),
                )
                
                // Date picker will be added here in the next step
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (eventName.isNotBlank()) {
                        onEventAdded(eventName, selectedDate)
                        onDismiss()
                    }
                },
                enabled = eventName.isNotBlank(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    )
}