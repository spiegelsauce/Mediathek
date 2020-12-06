package com.example.orfdownloader.data

class BroadcastArray : ArrayList<BroadcastArrayItem>()

data class BroadcastArrayItem(
    val broadcasts: List<Broadcast>,
    val date: Long,
    val dateISO: String,
    val dateOffset: Int,
    val day: Int
)

data class Broadcast(
    val description: String?,
    val end: Long,
    val endISO: String,
    val endOffset: Int,
    val entity: String,
    val isBroadcasted: Boolean,
    val isPublic: Boolean,
    val programKey: String,
    val scheduled: Long,
    val scheduledISO: String,
    val scheduledOffset: Int,
    val start: Long,
    val startISO: String,
    val startOffset: Int,
    val subtitle: String?,
    val title: String
)