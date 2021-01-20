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

data class StreamURLResponseItemV2(
        val title: String,
        val subtitle: String,
        val broadcastDay: Int,
        val description: String,
        val end: Long,
        val endISO: String,
        val endOffset: Int,
        val start: Long,
        val startISO: String,
        val startOffset: Int,
        val streams: List<Stream>,
        val images: List<Image>?
)

data class Image(
    val category: String,
    val versions : List<ImgVersion>
)

data class ImgVersion(
    val width : Int,
    val path: String,
    val hashCode: Int
)

data class Stream(
        val alias: String,
        val title: Any,
        val loopStreamId: String,
        val start: Long,
        val startISO: String,
        val startOffset: Int,
        val end: Long,
        val endISO: String,
        val endOffset: Int
)