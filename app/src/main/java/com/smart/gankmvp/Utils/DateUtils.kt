package com.smart.gankmvp.Utils

import java.util.*

object DateUtils {
    fun isSameDate(date1: Date, date2: Date): Boolean {

        val cal = Calendar.getInstance()
        cal.time = date1
        val selectedDate = Calendar.getInstance()
        selectedDate.time = date2

        return cal.get(Calendar.DAY_OF_YEAR) == selectedDate.get(Calendar.DAY_OF_YEAR)
    }

}