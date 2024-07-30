package com.practicum.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackListAdapter(val trackList: List<Track>):RecyclerView.Adapter<TrackListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_list_view_holder, parent, false)
        return TrackListViewHolder(view)
    }

    override fun getItemCount(): Int {
    return trackList.size
    }

    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) {
        holder.bind(trackList[position])
    }

}