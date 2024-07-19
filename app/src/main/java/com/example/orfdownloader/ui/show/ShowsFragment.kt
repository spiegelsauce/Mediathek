package com.example.orfdownloader.ui.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.orfdownloader.R
import com.example.orfdownloader.data.Selections
import com.example.orfdownloader.data.ShowDetails
import com.example.orfdownloader.ui.player.PlayerFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShowsFragment : Fragment() {

    companion object {
        fun newInstance() = ShowsFragment()
    }

    @Inject lateinit var selections: Selections

    private val showsViewModel by viewModels<ShowsViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ShowsFragmentContent(showsViewModel, playShow = { setShow(it) })
            }
        }
    }

    private fun setShow(show: ShowDetails) {
        showsViewModel.setShow(show)
        requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PlayerFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }
}