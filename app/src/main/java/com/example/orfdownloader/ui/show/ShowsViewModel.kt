package com.example.orfdownloader.ui.show

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orfdownloader.data.BroadcastState
import com.example.orfdownloader.data.Selections
import com.example.orfdownloader.data.ShowDetails
import com.example.orfdownloader.network.NetworkManager
import com.example.orfdownloader.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val selection: Selections,
    private val networkManager: NetworkManager) : ViewModel() {

    val showDetails = MutableLiveData<Map<Int, List<ShowDetails>>>()
    val loading = MutableLiveData<Boolean>()

    init {
        fetchAvailableShows()
    }

    private fun fetchAvailableShows() {
        loading.value = true
        showDetails.value = emptyMap()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("Exception", exception.message.orEmpty())
        }

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val shows: HashMap<Int, ArrayList<ShowDetails>> = HashMap()
            networkManager.getStreams()
                .forEach { item ->
                    val showsList = ArrayList<ShowDetails>()
                    item.broadcasts.forEach { bc ->
                        if (bc.state == BroadcastState.C) { //only add if this broadcast is available for playback TODO: show future shows but disable playback
                            showsList.add(
                                ShowDetails(
                                    bc.broadcastDay,
                                    bc.programKey,
                                    DateUtil.getDate(
                                        bc.scheduledStartISO
                                    ),
                                    bc.title,
                                    bc.subtitle.orEmpty(),
                                    bc.description.orEmpty(),
                                    bc.url
                                )
                            )
                        }
                    }
                    if (showsList.isNotEmpty()) {
                        shows[item.day] = showsList
                    }
                }
            showDetails.value = shows.toSortedMap()
            loading.value = false
        }
    }

    fun setShow(details: ShowDetails) {
        selection.show = details
    }


}