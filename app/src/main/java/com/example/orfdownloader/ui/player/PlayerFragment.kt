package com.example.orfdownloader.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.orfdownloader.cast.CastManager
import com.example.orfdownloader.cast.SessionManagerAdapter
import com.google.android.gms.cast.framework.Session
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlayerFragment : Fragment(), SessionManagerAdapter {

    private val playerViewModel by viewModels<PlayerViewModel>()

    private lateinit var exoPlayer: ExoPlayer

    private var streamUri: List<String> = ArrayList()

    @Inject
    lateinit var castManager: CastManager

    companion object {
        fun newInstance() = PlayerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MyPlayerView(playerViewModel)
            }

//
//
//
//        binding = DataBindingUtil.inflate(inflater, R.layout.player_fragment, container, false)
//        binding.viewModel = playerViewModel
//        binding.lifecycleOwner = this
//this//
//            exoPlayer = ExoPlayer.Builder(requireContext())
//                .build()
////
//        playerViewModel.castDeviceConnected.value = castManager.castDeviceConnected
//        playerViewModel.fetchStreams()
//
//        castManager.sessionManager.addSessionManagerListener(this)
//
//        binding.playerView.player = exoPlayer
//
//        val observer = Observer<List<String>> { uri ->
//            streamUri = uri
//            if (castManager.castDeviceConnected) {
//                castManager.castStream(
//                    uri,
//                    playerViewModel.showImageUrl.value?.ceilingEntry(500)?.value
//                )
//            } else {
//                exoPlayer.run {
//                    clearMediaItems()
//                    uri.forEach { addMediaItem(MediaItem.fromUri(it)) }
//                    prepare()
//                    play()
//                }
//            }
//        }
//
//        playerViewModel.streamUri.observe(viewLifecycleOwner, observer)
//
//        return binding.root
        }
    }

    override fun onDestroyView() {
//        binding.playerView.playerViewΩyer?.release()
        super.onDestroyView()
    }

    //Cast Listeners
    override fun onSessionStarted(p0: Session?, p1: String?) = castStart()
    override fun onSessionEnding(p0: Session?) = castEnd()

    private fun castStart() {
        playerViewModel.castDeviceConnected.value = true
        castManager.castStream(
            streamUri,
            playerViewModel.showImageUrl.value,
            exoPlayer.currentPosition
        )
        exoPlayer.stop()
    }

    private fun castEnd() {
        playerViewModel.castDeviceConnected.value = false
        exoPlayer.run {
            if (currentMediaItem?.localConfiguration?.uri.toString() != castManager.getCurrentQueueItem()) {
                streamUri.forEach { addMediaItem(MediaItem.fromUri(it)) }
            }

            seekTo(castManager.getCurrentPosition())
            prepare()
            play()
        }
    }

}