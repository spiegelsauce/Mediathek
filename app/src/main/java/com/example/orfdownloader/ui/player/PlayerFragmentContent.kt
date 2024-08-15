package com.example.orfdownloader.ui.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.orfdownloader.R

@Composable
fun MyPlayerView(viewModel: PlayerViewModel) {

    val loading by viewModel.isLoading.observeAsState()

    val url by viewModel.streamUri.observeAsState()

    val showImageUrl by viewModel.showImageUrl.observeAsState()

    val density = LocalDensity.current

    var widthInPx by remember { mutableStateOf(0) }
    var imageUrl by remember{ mutableStateOf("")}

    if (loading == true) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            RadioWithWaves()
        }
        return
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned { viewModel.width = it.size.width }) {
        showImageUrl?.let {url ->
            Box {
                AsyncImage(
                    model = url,
                    contentDescription = "Show Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f / 2)
                )
            }
        }
        Text(viewModel.title.toAnnotatedString())
        Text(viewModel.subTitle.toAnnotatedString())
        Text(viewModel.description.toAnnotatedString())
        // Exoplayer

//        SimpleAudioPlayer(url)

    }
}

@Composable
fun SimpleAudioPlayer(url: List<String>?) {
    val context = LocalContext.current

    // Create an ExoPlayer instance
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {

            clearMediaItems()
            url?.forEach { addMediaItem(MediaItem.fromUri(it)) }
            prepare()
            playWhenReady = true
        }
    }

    // Dispose of the ExoPlayer instance when not needed
    DisposableEffect(
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}