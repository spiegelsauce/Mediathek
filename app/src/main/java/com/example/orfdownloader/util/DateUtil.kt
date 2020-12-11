package com.example.orfdownloader.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    /*
    Parse Date object from ISO String
     */
    fun getDate(rawDate: String, inputFormat: DateFormat = DateFormat.ISO): Date {
        val sdf = SimpleDateFormat(inputFormat.pattern, Locale.getDefault())
        return sdf.parse(rawDate) ?: Date()
    }

    fun convertDate(date: Date, outputFormat: DateFormat): String {
        val sdf = SimpleDateFormat(outputFormat.pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT+1")
        return date.let { sdf.format(it) }.orEmpty()
    }

    fun convertDate(
        inputString: String,
        inputFormat: DateFormat,
        outputFormat: DateFormat
    ): String {
        val sdf = SimpleDateFormat(inputFormat.pattern, Locale.getDefault())
        val date = sdf.parse(inputString)
        sdf.applyPattern(outputFormat.pattern)
        sdf.timeZone = TimeZone.getTimeZone("GMT+1")
        return date?.let { sdf.format(it) }.orEmpty()
    }

    enum class DateFormat(val pattern: String) {
        ISO("yyyy-MM-dd'T'HH:mm:ssX"),
        rawShowDate("yyyyMMdd"),
        niceShowDate("EEE'\n'MM/dd"),
        niceTime("HH:mm"),
        dayOfWeek("EEE")
    }


}