package com.example.orfdownloader.ui.show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orfdownloader.R
import com.example.orfdownloader.data.ShowDetails
import com.example.orfdownloader.util.DateUtil
import kotlinx.android.synthetic.main.show_titles_view.view.*

class ShowTitlesAdapter(private val onDetailClickListener: (key: ShowDetails) -> Unit) : RecyclerView.Adapter<ShowTitlesAdapter.ShowTitlesViewHolder>() {

    inner class ShowTitlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onDetailClickListener(titles[adapterPosition]) }
        }
    }

    var titles = ArrayList<ShowDetails>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowTitlesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_titles_view, parent, false)
        return ShowTitlesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowTitlesViewHolder, position: Int) {
        val timeString = DateUtil.convertDate(titles[position].startTime, DateUtil.DateFormat.NICETIME)

        holder.itemView.let {
            it.show_title.text =
                it.context.getString(
                    R.string.show_description,
                    timeString,
                    titles[position].showTitle
                )
        }
    }

    override fun getItemCount() = titles.size
}

