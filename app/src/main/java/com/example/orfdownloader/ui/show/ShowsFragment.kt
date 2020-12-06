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
import com.example.orfdownloader.R
import com.example.orfdownloader.data.ShowDetails
import com.example.orfdownloader.databinding.ShowsFragmentBinding
import com.example.orfdownloader.ui.player.PlayerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowsFragment : Fragment() {

    companion object {
        fun newInstance() = ShowsFragment()
    }

    private val showsViewModel by viewModels<ShowsViewModel>()

    private lateinit var binding: ShowsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.shows_fragment, container, false)
        binding.viewmodel = showsViewModel
        binding.lifecycleOwner = this

        val showDetailsObserver = Observer<Map<Int, List<ShowDetails>>> obs@{ shows ->
            if (shows.isEmpty()) return@obs

            val days = shows.keys.toList().sorted()

            binding.showsChooserRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter =
                    ShowsAdapter(
                        { d: Int -> dayExpand(d) },
                        { k: ShowDetails -> setShow(k) }).apply {
                        setDays(days)
                        addDetails(days[0], shows.getValue(days[0]))
                    }
            }
        }

        showsViewModel.showDetails.observe(viewLifecycleOwner, showDetailsObserver)

        return binding.root
    }

    private fun dayExpand(day: Int) {
        (binding.showsChooserRecyclerView.adapter as ShowsAdapter).addDetails(
            day,
            showsViewModel.showDetails.value?.get(day).orEmpty()
        )
    }

    private fun setShow(show: ShowDetails) {
        showsViewModel.setShow(show)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PlayerFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }


}