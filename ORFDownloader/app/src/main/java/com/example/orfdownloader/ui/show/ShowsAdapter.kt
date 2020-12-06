package com.example.orfdownloader.ui.show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orfdownloader.R
import com.example.orfdownloader.data.*
import com.example.orfdownloader.util.DateUtil
import com.example.orfdownloader.util.DateUtil.DateFormat.*
import kotlinx.android.synthetic.main.show_day_view.view.*
import kotlinx.android.synthetic.main.show_details_view.view.*

class ShowsAdapter(
    private val onDayClickListener: ((day: Int) -> Unit),
    private val onDetailClickListener: (key: ShowDetails) -> Unit
) : RecyclerView.Adapter<ShowsAdapter.ShowsViewHolder>() {

    inner class ShowsViewHolder(showsView: View) : RecyclerView.ViewHolder(showsView) {
        init {
            itemView.setOnClickListener {
                when (getItemViewType(adapterPosition)) {
                    showDayType -> {
                        onDayClickListener((backingList[adapterPosition] as ShowDay).date)
                    }
                    showDetailsType -> {
                        onDetailClickListener(backingList[adapterPosition] as ShowDetails)
                    }
                }
            }
        }
    }

    private var daysOfShow: List<Int> = ArrayList()
    private var backingList = ArrayList<ShowBackingData>()

    fun setDays(days: List<Int>) {
        daysOfShow = days
        backingList.clear()
        days.mapTo(backingList) { ShowDay(it) }
    }

    fun addDetails(day: Int, details: List<ShowDetails>) {
        val positionStart = backingList.indexOfFirst { it.getType() == showDetailsType }
        val count = backingList.count { it.getType() == showDetailsType }
        backingList.removeIf { it.getType() == showDetailsType }
        notifyItemRangeRemoved(positionStart, count)

        backingList.addAll(daysOfShow.indexOf(day).inc(), details)
        notifyItemRangeInserted(daysOfShow.indexOf(day).inc(), details.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            when (viewType) {
                showDayType -> R.layout.show_day_view
                showDetailsType -> R.layout.show_details_view
                else -> 0
            },
            parent, false
        )
        return ShowsViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int = backingList[position].getType()

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        if (getItemViewType(position) == showDayType) {
            holder.itemView.show_day.text = DateUtil.convertDate(
                (backingList[position] as ShowDay).date.toString(),
                rawShowDate,
                niceShowDate
            )
        } else if (getItemViewType(position) == showDetailsType) {
            val details = (backingList[position] as ShowDetails)
            holder.itemView.show_time.text = DateUtil.convertDate(details.startTime, niceTime)
            holder.itemView.show_title.text = details.showTitle
        }
    }

    override fun getItemCount(): Int = backingList.size
}
