package com.example.orfdownloader.data

import java.util.*

data class ShowDetails(
    val day: Int,
    val programKey: String,
    val startTime: Date,
    val showTitle: String,
    val showSubtitle: String,
    val showDescription: String,
    val showUrl: String
)