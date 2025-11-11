package com.example.medicine_reminder.ui

data class Medicine(
    val name: String,
    val time: String,
    var status: String = "Added"
)
