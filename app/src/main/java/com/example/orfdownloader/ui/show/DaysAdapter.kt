package com.example.orfdownloader.ui.show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.orfdownloader.R
import com.example.orfdownloader.util.DateUtil
import kotlinx.android.synthetic.main.show_day_view_v2.view.*

class DaysAdapter(private val onDayClickListener: ((day: Int) -> Unit)) : RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {
    var daysOfShow: List<Int> = ArrayList()
    var selectedPos: Int = 0

    inner class DaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_day_view_v2,
                parent, false
        )
        return DaysViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        val date = DateUtil.getDate(daysOfShow[position].toString(), DateUtil.DateFormat.rawShowDate)
        holder.itemView.show_day.text = DateUtil.convertDate(date, DateUtil.DateFormat.niceShowDate)

        holder.itemView.show_day.background = ContextCompat.getDrawable(holder.itemView.context,
                if (position == selectedPos) R.color.dayFocused else R.color.dayUnFocused
        )
    }

    override fun getItemCount() = daysOfShow.size

}
