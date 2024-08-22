package com.toren.domain.model.alarm

data class Alarm(
    val id: Int,
    val time: String,
    val message: String,
    var enabled: Boolean
)
