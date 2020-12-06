package com.example.orfdownloader.network

import com.example.orfdownloader.data.*
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject



interface OrfApis {
    @GET
    suspend fun fetchStreams(@Url url : String): BroadcastArray

    @GET
    suspend fun fetchStreamIds(@Url url: String): StreamURLResponseItem
}

class NetworkManager @Inject constructor(val selections: Selections, private val service: OrfApis) {

    suspend fun getStreams() = service.fetchStreams("${selections.station.broadcastUrlKey}/json/2.0/broadcasts")

    suspend fun getStreamIds() = service.fetchStreamIds("https://audioapi.orf.at/${selections.station.broadcastUrlKey}/json/2.0/playlist/${selections.show!!.programKey}${selections.show!!.dayOfWeekAffix}")


}