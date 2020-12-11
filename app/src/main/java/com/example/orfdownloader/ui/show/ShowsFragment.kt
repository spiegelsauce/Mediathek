package com.example.orfdownloader.ui.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.example.orfdownloader.R
import com.example.orfdownloader.data.ShowDetails
import com.example.orfdownloader.databinding.ShowsFragmentV2Binding
import com.example.orfdownloader.ui.player.PlayerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowsFragment : Fragment() {

    val v2 = true

    companion object {
        fun newInstance() = ShowsFragment()
    }

    private val showsViewModel by viewModels<ShowsViewModel>()

    private lateinit var binding: ShowsFragmentV2Binding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding =
                DataBindingUtil.inflate(inflater, R.layout.shows_fragment_v2, container, false)
        binding.viewmodel = showsViewModel
        binding.lifecycleOwner = this

        val showDetailsObserver = Observer<Map<Int, List<ShowDetails>>> obs@{ shows ->
            if (shows.isEmpty()) return@obs

            val days = shows.keys.toList().sorted()

            binding.showsList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ShowTitlesAdapter { k: ShowDetails -> setShow(k) }
            }

            binding.daysScroller.apply {
                layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
                adapter = DaysAdapter { d: Int -> setDay(d) }.apply {
                    daysOfShow = days.asReversed()
                    setDay(daysOfShow[selectedPos])
                }
            }
        }

        showsViewModel.showDetails.observe(viewLifecycleOwner, showDetailsObserver)

        return binding.root
    }

    private fun setDay(day: Int) {
        (binding.showsList.adapter as ShowTitlesAdapter).titles = showsViewModel.showDetails.value?.get(day).orEmpty() as ArrayList<ShowDetails>
    }

    private fun setShow(show: ShowDetails) {
        showsViewModel.setShow(show)
        requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PlayerFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }


}