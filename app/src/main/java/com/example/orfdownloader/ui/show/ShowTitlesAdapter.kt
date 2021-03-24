package com.example.orfdownloader.ui.show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orfdownloader.R
import com.example.orfdownloader.data.ShowDetails
import com.example.orfdownloader.databinding.ShowTitlesViewBinding
import com.example.orfdownloader.util.DateUtil

class ShowTitlesAdapter(private val onDetailClickListener: (key: ShowDetails) -> Unit) :
    RecyclerView.Adapter<ShowTitlesAdapter.ShowTitlesViewHolder>() {

    inner class ShowTitlesViewHolder(val binding: ShowTitlesViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
        val binding =
            ShowTitlesViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowTitlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowTitlesViewHolder, position: Int) {
        val timeString =
            DateUtil.convertDate(titles[position].startTime, DateUtil.DateFormat.NICETIME)

        holder.binding.apply {
            showTitle.text =
                root.context.getString(
                    R.string.show_description,
                    timeString,
                    titles[position].showTitle
                )
        }
    }

    override fun getItemCount() = titles.size
}

