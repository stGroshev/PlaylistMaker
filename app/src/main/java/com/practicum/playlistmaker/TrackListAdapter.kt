package com.practicum.playlistmaker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackListAdapter():RecyclerView.Adapter<TrackListViewHolder>() {

    private val list = ArrayList<Track>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder = TrackListViewHolder(parent)

    override fun getItemCount(): Int {
    return list.size
    }

    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) {
        holder.bind(list[position])
    }

}