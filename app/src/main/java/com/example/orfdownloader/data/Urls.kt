package com.example.orfdownloader.data

fun getStreamUrls(station: String, streamId: List<String>) = streamId.map{ "https://loopstream01.apa.at/?channel=$station&id=$it" }