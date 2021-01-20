package com.example.orfdownloader.cast

import android.net.Uri
import com.example.orfdownloader.data.Selections
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.MediaMetadata
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

    fun castStream(uri: String, imageUri: String?, startPosition: Long = 0) {

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

        val mediaInfo = MediaInfo.Builder(uri)
            .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
            .setContentType("audio/mpeg3")
            .setMetadata(audioTrackMetaData)
            .build()

        val requestData: MediaLoadRequestData =
            MediaLoadRequestData.Builder()
                .setMediaInfo(mediaInfo)
                .setQueueData(null)
                .setCurrentTime(startPosition)
                .build()

        remoteMediaClient.load(requestData)

    }

    fun getCurrentPosition(): Long =
        sessionManager.currentCastSession?.remoteMediaClient?.approximateStreamPosition ?: 0
}