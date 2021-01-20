package com.example.orfdownloader.data

import com.google.gson.annotations.SerializedName

class BroadcastArray : ArrayList<BroadcastArrayItem>()

data class BroadcastArrayItem(
    val broadcasts: List<Broadcast>,
    val day: Int
)

data class Broadcast(
    @SerializedName("href") val url: String,
    val broadcastDay: Int,
    val title: String,
    val subtitle: String?,
    val description: String?,
    val programKey: String,
    val scheduledStart: Long,
    val scheduledStartISO: String,
    val start: Long,
    val end: Long,
    val state: BroadcastState
)

enum class BroadcastState {
    C, //Complete
    P, //In Progress
    S //Scheduled
}