package com.example.orfdownloader.data

data class StreamURLResponseItem(
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