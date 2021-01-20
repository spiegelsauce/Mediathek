package com.example.orfdownloader.ui.player

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.orfdownloader.R
import com.example.orfdownloader.cast.CastManager
import com.example.orfdownloader.cast.SessionManagerAdapter
import com.example.orfdownloader.databinding.PlayerFragmentBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.gms.cast.framework.Session
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlayerFragment : Fragment(), SessionManagerAdapter {

    private val playerViewModel by viewModels<PlayerViewModel>()

    private lateinit var exoPlayer: ExoPlayer

    private var streamUri: String = ""

    @Inject
    lateinit var castManager: CastManager

    companion object {
        fun newInstance() = PlayerFragment()
    }

    private lateinit var binding: PlayerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.player_fragment, container, false)
        binding.viewModel = playerViewModel
        binding.lifecycleOwner = this

        exoPlayer = SimpleExoPlayer.Builder(requireContext())
            .build()

        playerViewModel.castDeviceConnected.value = castManager.castDeviceConnected
        playerViewModel.fetchStreams()

        castManager.sessionManager.addSessionManagerListener(this)

        binding.playerView.player = exoPlayer

        val observer = Observer<String> { uri ->
            streamUri = uri
            if (castManager.castDeviceConnected) {
                castManager.castStream(
                    streamUri,
                    playerViewModel.showImageUrl.value?.ceilingEntry(500)?.value
                )
            } else {
                exoPlayer.run {
                    setMediaItem(MediaItem.fromUri(uri))
                    prepare()
                    play()
                }
            }
        }

        playerViewModel.streamUri.observe(viewLifecycleOwner, observer)

        return binding.root
    }

    override fun onDestroyView() {
        binding.playerView.player?.release()
        super.onDestroyView()
    }

    //Cast Listeners
    override fun onSessionStarted(p0: Session?, p1: String?) = castStart()
    override fun onSessionEnding(p0: Session?) = castEnd()

    private fun castStart() {
        playerViewModel.castDeviceConnected.value = true
        castManager.castStream(
            streamUri,
            playerViewModel.showImageUrl.value?.floorEntry(500)?.value,
            exoPlayer.currentPosition
        )
        exoPlayer.stop()
    }

    private fun castEnd() {
        playerViewModel.castDeviceConnected.value = false
        exoPlayer.run {
            if (currentMediaItem?.playbackProperties?.uri != Uri.parse(streamUri)) {
                setMediaItem(MediaItem.fromUri(streamUri))
            }
            seekTo(castManager.getCurrentPosition())
            prepare()
            play()
        }
    }

}