package com.example.orfdownloader.ui.player

import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orfdownloader.data.Selections
import com.example.orfdownloader.data.getStreamUrl
import com.example.orfdownloader.network.NetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlayerViewModel @ViewModelInject constructor(
    private val selections: Selections,
    private val networkManager: NetworkManager
) : ViewModel() {

    val title: Spanned = formatHtml(selections.show?.showTitle.orEmpty())
    val subTitle: Spanned = formatHtml(selections.show?.showSubtitle.orEmpty())
    val description: Spanned = formatHtml(selections.show?.showDescription.orEmpty())

    val streamUri = MutableLiveData<String>()

    fun fetchStreams() {
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            val streamId = networkManager.getStreamIds().streams[0].loopStreamId
            streamUri.value = getStreamUrl(selections.station.loopStreamUrlKey, streamId)
        }
    }

    private fun formatHtml(text: String) = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)

}