package com.example.orfdownloader.ui.player

import android.text.Html
import android.text.Spanned
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orfdownloader.data.Selections
import com.example.orfdownloader.data.getStreamUrl
import com.example.orfdownloader.network.NetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

const val programImageKey = "imgprog"

class PlayerViewModel @ViewModelInject constructor(
    private val selections: Selections,
    private val networkManager: NetworkManager
) : ViewModel() {

    val title: Spanned = formatHtml(selections.show?.showTitle.orEmpty())
    val subTitle: Spanned = formatHtml(selections.show?.showSubtitle.orEmpty())
    val description: Spanned = formatHtml(selections.show?.showDescription.orEmpty())

    val showImageUrl = MutableLiveData<TreeMap<Int, String>>()
    val streamUri = MutableLiveData<String>()
    val castDeviceConnected = MutableLiveData<Boolean>()
    var streamDuration: Long = 0

    fun fetchStreams() {

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {

            val response = networkManager.getBroadcastDetails(selections.show!!.showUrl)
            val streamId = response.streams[0].loopStreamId
            streamDuration = response.streams[0].run { end - start }

            showImageUrl.value =
                response.images?.firstOrNull()?.let{ image ->
                    image.versions.associate {
                    Pair(it.width, it.path)
                }.toSortedMap() as TreeMap<Int, String>}


            streamUri.value = getStreamUrl(selections.station.loopStreamUrlKey, streamId)
        }
    }

    private fun formatHtml(text: String) = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)

}