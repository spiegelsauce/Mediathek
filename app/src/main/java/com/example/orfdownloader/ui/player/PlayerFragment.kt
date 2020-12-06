package com.example.orfdownloader.ui.player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.orfdownloader.R
import com.example.orfdownloader.databinding.PlayerFragmentBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private val playerViewModel by viewModels<PlayerViewModel>()

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

        playerViewModel.fetchStreams()

        val exoPlayer = SimpleExoPlayer.Builder(requireContext())
            .build()
        binding.playerView.setPlayer(exoPlayer)

        val observer = Observer<String> { uri ->
            exoPlayer.run {
                addMediaItem(MediaItem.fromUri(uri))
                prepare()
                play()
            }
        }

        playerViewModel.streamUri.observe(viewLifecycleOwner, observer)

        return binding.root
    }

    override fun onDestroyView() {
        binding.playerView.player?.release()
        super.onDestroyView()
    }
}