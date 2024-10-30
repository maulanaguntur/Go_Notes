package com.lamz.go_notes.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Utils {
    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        return currentDate.format(formatter)
    }
}