package com.example.orfdownloader.ui.station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.orfdownloader.R
import com.example.orfdownloader.data.RadioStations
import kotlinx.android.synthetic.main.station_view.view.*

class StationAdapter(private val stations: Array<RadioStations>, private val onItemClickListener: (station: RadioStations) -> Unit) :
    RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    inner class StationViewHolder(val stationView: CardView) : RecyclerView.ViewHolder(stationView) {
        init {
            itemView.setOnClickListener {
                onItemClickListener(stations[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val stationView = LayoutInflater.from(parent.context)
            .inflate(R.layout.station_view, parent, false) as CardView
        return StationViewHolder(stationView)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.stationView.run {
            station_name.text = stations[position].humanFriendlyName
            station_ic.setImageDrawable(
                ContextCompat.getDrawable(
                    this.context,
                    stations[position].icon
                )
            )
        }
    }

    override fun getItemCount(): Int = stations.size
}