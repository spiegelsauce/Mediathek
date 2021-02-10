package com.example.orfdownloader.cast

import android.net.Uri
import com.example.orfdownloader.data.Selections
import com.google.android.gms.cast.*
import com.google.android.gms.cast.framework.SessionManager
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import com.google.android.gms.common.images.WebImage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CastManager @Inject constructor(
    val sessionManager: SessionManager,
    private val selections: Selections
) : SessionManagerAdapter {

    val castDeviceConnected get() = sessionManager.currentCastSession != null

    fun castStream(uri: List<String>, imageUri: String?, startPosition: Long = 0) {

        val remoteMediaClient: RemoteMediaClient =
            sessionManager.currentCastSession?.remoteMediaClient
                ?: return

        val audioTrackMetaData = MediaMetadata(MediaMetadata.MEDIA_TYPE_MUSIC_TRACK).apply {
            putString(
                MediaMetadata.KEY_TITLE,
                "${selections.station.humanFriendlyName} - ${selections.show?.showTitle}"
            )
            putString(MediaMetadata.KEY_SUBTITLE, selections.show?.showSubtitle)
            imageUri?.let { addImage(WebImage(Uri.parse(it))) }
        }

        val queueItems: List<MediaQueueItem> = uri.map {
            val queueItem = MediaInfo.Builder(it)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType("audio/mpeg3")
                .setMetadata(audioTrackMetaData)
                .build()

            MediaQueueItem.Builder(queueItem)
                .setAutoplay(true)
                .setPreloadTime(20.0)
                .build()
        }


        val requestData: MediaLoadRequestData =
            MediaLoadRequestData.Builder()
                .setQueueData(
                    MediaQueueData.Builder().setItems(queueItems).build()
                )
                .setCurrentTime(startPosition)
                .build()

        remoteMediaClient.load(requestData)
    }

    fun getCurrentPosition(): Long =
        sessionManager.currentCastSession?.remoteMediaClient?.approximateStreamPosition ?: 0

    fun getCurrentQueueItem(): String? {
        return sessionManager.currentCastSession.remoteMediaClient.currentItem.media.contentId
    }

}