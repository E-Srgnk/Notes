package com.srgnk.simplenotes.mvp.utils

import java.text.SimpleDateFormat
import java.util.*

fun getFormattedDate(date: Date): String {
    val formatter = SimpleDateFormat("dd MMM yyyy  HH:mm", Locale.ROOT)
    return formatter.format(date)
}