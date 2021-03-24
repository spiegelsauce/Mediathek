package com.example.orfdownloader.ui.show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.orfdownloader.data.RadioStations
import com.example.orfdownloader.databinding.ShowDayViewBinding
import com.example.orfdownloader.util.DateUtil

class DaysAdapter(
    private val onDayClickListener: ((day: Int) -> Unit),
    private val station: RadioStations
) :
    RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {

    var daysOfShow: List<Int> = ArrayList()
    var selectedPos: Int = 0

    inner class DaysViewHolder(val binding: ShowDayViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                selectDay(adapterPosition)
                onDayClickListener(daysOfShow[adapterPosition])
            }
        }
    }

    private fun selectDay(position: Int) {
        val previousSelectedPos = selectedPos
        selectedPos = position
        notifyItemChanged(previousSelectedPos)
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val binding = ShowDayViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return DaysViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        val date =
            DateUtil.getDate(daysOfShow[position].toString(), DateUtil.DateFormat.RAWSHOWDATE)
        holder.binding.showDay.apply {
            text = DateUtil.convertDate(date, DateUtil.DateFormat.NICESHOWDATE)
            isSelected = position == selectedPos
            background = ContextCompat.getDrawable(
                holder.itemView.context,
                station.bgcolor
            )
        }

    }

    override fun getItemCount() = daysOfShow.size

}
