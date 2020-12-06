package com.example.orfdownloader.data

import java.util.*

const val showDayType = 0
const val showDetailsType = 1

interface ShowBackingData {
    fun getType(): Int
}

data class ShowDay(val date: Int) : ShowBackingData {
    override fun getType() = showDayType
}

data class ShowDetails(
    val dayOfWeekAffix: String,
    val programKey: String,
    val startTime: Date,
    val showTitle: String,
    val showSubtitle: String,
    val showDescription: String
) : ShowBackingData {
    override fun getType() = showDetailsType
}