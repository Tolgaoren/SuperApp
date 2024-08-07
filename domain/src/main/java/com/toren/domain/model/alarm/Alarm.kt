package com.toren.domain.model.alarm

data class Alarm(
    val id: Int,
    val time: Long,
    val message: String,
    val enabled: Boolean
)
