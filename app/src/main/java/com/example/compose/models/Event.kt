package com.example.compose.models

import java.util.Date

data class Event(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val date: Date
) 