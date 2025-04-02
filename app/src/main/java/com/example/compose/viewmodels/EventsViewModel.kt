package com.example.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.models.Event
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventsViewModel : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        val calendar = Calendar.getInstance()
        
        // Android Dev Summit
        calendar.set(2024, Calendar.MAY, 15)
        val androidDevSummit = Event(name = "Android Dev Summit", date = calendar.time)
        
        // KotlinConf
        calendar.set(2024, Calendar.JUNE, 20)
        val kotlinConf = Event(name = "KotlinConf", date = calendar.time)
        
        // Google I/O
        calendar.set(2024, Calendar.JULY, 10)
        val googleIO = Event(name = "Google I/O", date = calendar.time)
        
        _events.value = listOf(androidDevSummit, kotlinConf, googleIO)
    }

    fun addEvent(name: String, date: Date) {
        val newEvent = Event(name = name, date = date)
        _events.value += newEvent
    }

    fun deleteEvent(eventId: String) {
        _events.value = _events.value.filter { it.id != eventId }
    }
}

class EventsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 