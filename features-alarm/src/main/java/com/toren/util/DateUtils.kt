package com.toren.util

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toDateLong() : Long {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.parse(this)?.time ?: 0
}

fun Long.toDateString() : String {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.format(this)
}


fun String.toFormatedDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm\ndd MMMM", Locale.getDefault())
    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) } ?: ""
}