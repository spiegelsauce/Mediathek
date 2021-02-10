package com.example.orfdownloader.network

import com.example.orfdownloader.data.*
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

interface OrfApis {
    @GET
    suspend fun fetchStreams(@Url url : String): BroadcastArray

    @GET
    suspend fun fetchBroadCastDetails(@Url url:String) : StreamURLResponseItem
}

class NetworkManager @Inject constructor(val selections: Selections, private val service: OrfApis) {

    /**
     * Get all the available Shows and Streams for a given Station
     */
    suspend fun getStreams() = service.fetchStreams("${selections.station.broadcastUrlKey}/json/4.0/broadcasts")

    /**
     * Get the details for a given Broadcast
     */
    suspend fun getBroadcastDetails(url: String) = service.fetchBroadCastDetails(url)
}