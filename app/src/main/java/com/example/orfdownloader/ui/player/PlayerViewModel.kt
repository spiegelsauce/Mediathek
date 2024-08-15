package com.example.orfdownloader.ui.player

import android.graphics.Typeface
import android.text.Html
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orfdownloader.data.Selections
import com.example.orfdownloader.data.getStreamUrls
import com.example.orfdownloader.network.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val selections: Selections,
    private val networkManager: NetworkManager
) : ViewModel() {
    // UI State

    val isLoading : MutableLiveData<Boolean> = MutableLiveData(true)

    val title: Spanned = formatHtml(selections.show?.showTitle.orEmpty())
    val subTitle: Spanned = formatHtml(selections.show?.showSubtitle.orEmpty())
    val description: Spanned = formatHtml(selections.show?.showDescription.orEmpty())

    val imageLoading = MutableLiveData<Boolean>()
    val showImageUrl = MutableLiveData<String>()
    val streamUri = MutableLiveData<List<String>>()
    val castDeviceConnected = MutableLiveData<Boolean>()

    var width : Int = 0

    private var streamDuration: Long = 0

    init {
        fetchStreams()
    }
    private fun fetchStreams() {

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {

            val response = networkManager.getBroadcastDetails(selections.show!!.showUrl)

            val streamIds = response.streams.map { it.loopStreamId }
            streamDuration = response.streams[0].run { end - start }

            Log.d("###PlayerViewModel", "fetchStreams: ${response.images?.firstOrNull()?.versions}")


            val showImageUrls =
                response.images?.firstOrNull()?.let { image ->
                    image.versions.associate {
                        Pair(it.width, it.path)
                    }.toSortedMap() as TreeMap<Int, String>
                }
            val showImageUrlPair = showImageUrls?.ceilingEntry(width) ?: showImageUrls?.lastEntry()
            showImageUrl.value = showImageUrlPair?.value

            streamUri.value = getStreamUrls(selections.station.loopStreamUrlKey, streamIds)

            isLoading.value = false
        }
    }

    private fun formatHtml(text: String) = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)

}

/**
 * Converts a [Spanned] into an [AnnotatedString] trying to keep as much formatting as possible.
 *
 * Currently supports `bold`, `italic`, `underline` and `color`.
 */
fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString
    Log.d("### PlayerVm", "toAnnotatedString: $spanned")
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        Log.d("### PlayerVM", "toAnnotatedString: $span")
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    ), start, end
                )
            }

            is UnderlineSpan -> addStyle(
                SpanStyle(textDecoration = TextDecoration.Underline),
                start,
                end
            )

            is ForegroundColorSpan -> addStyle(
                SpanStyle(color = Color(span.foregroundColor)),
                start,
                end
            )
        }
    }
}