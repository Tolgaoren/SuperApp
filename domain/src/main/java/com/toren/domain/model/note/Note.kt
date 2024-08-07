package com.toren.domain.model.note

data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long
)
