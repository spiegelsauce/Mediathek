package com.example.orfdownloader.ui.station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.orfdownloader.data.RadioStations
import com.example.orfdownloader.databinding.StationViewBinding

class StationAdapter(
    private val stations: Array<RadioStations>,
    private val onItemClickListener: (station: RadioStations) -> Unit
) :
    RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    inner class StationViewHolder(val binding: StationViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClickListener(stations[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binding = StationViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.binding.run {
            stationName.text = stations[position].humanFriendlyName
            stationIc.setImageDrawable(
                ContextCompat.getDrawable(
                    root.context,
                    stations[position].icon
                )
            )
        }
    }

    override fun getItemCount(): Int = stations.size
}