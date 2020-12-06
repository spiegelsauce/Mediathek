package com.example.orfdownloader.data
import com.google.gson.annotations.SerializedName

data class StreamURLResponseItem(
    @SerializedName("alias")
    val alias: String,
    @SerializedName("broadcastDay")
    val broadcastDay: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("end")
    val end: Long,
    @SerializedName("endISO")
    val endISO: String,
    @SerializedName("endOffset")
    val endOffset: Int,
    @SerializedName("start")
    val start: Long,
    @SerializedName("startISO")
    val startISO: String,
    @SerializedName("startOffset")
    val startOffset: Int,
    @SerializedName("streams")
    val streams: List<Stream>,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("title")
    val title: String
)

data class Stream(
    @SerializedName("alias")
    val alias: String,
    @SerializedName("end")
    val end: Long,
    @SerializedName("endISO")
    val endISO: String,
    @SerializedName("endOffset")
    val endOffset: Int,
    @SerializedName("loopStreamId")
    val loopStreamId: String,
    @SerializedName("start")
    val start: Long,
    @SerializedName("startISO")
    val startISO: String,
    @SerializedName("startOffset")
    val startOffset: Int,
    @SerializedName("title")
    val title: Any
)