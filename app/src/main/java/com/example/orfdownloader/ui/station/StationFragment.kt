package com.example.orfdownloader.ui.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orfdownloader.R
import com.example.orfdownloader.data.RadioStations
import com.example.orfdownloader.data.Selections
import com.example.orfdownloader.databinding.StationsFragmentBinding
import com.example.orfdownloader.ui.main.MainViewModel
import com.example.orfdownloader.ui.show.ShowsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StationFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    @Inject
    lateinit var selections: Selections

    companion object {
        fun newInstance() = StationFragment()
    }

    private lateinit var viewModel: MainViewModel

    private fun onClick(selectedStation: RadioStations) {
        //set station icon in the ToolBar
        (activity as AppCompatActivity).supportActionBar?.run {
            setIcon(selectedStation.icon)
            title = selectedStation.humanFriendlyName
        }

        selections.station = selectedStation
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ShowsFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: StationsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.stations_fragment, container, false)

        viewManager = LinearLayoutManager(this.context)

        binding.stationChooserRecyclerview.apply {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager


            adapter = StationAdapter(
                RadioStations.values()
            ) { station -> onClick(station) }
        }

        return binding.root
    }

    override fun onResume() {
        (activity as AppCompatActivity).supportActionBar?.run {
            setIcon(null)
            setTitle(R.string.StationChooserTitle)
        }
            super.onResume()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

    }

}