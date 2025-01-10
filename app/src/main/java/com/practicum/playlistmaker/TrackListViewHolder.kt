package com.practicum.playlistmaker


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackListViewHolder ( parent: ViewGroup):
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.track_list_view_holder, parent, false)
    ){
    private val poster = itemView.findViewById<ImageView>(R.id.poster)
    private val trackName = itemView.findViewById<TextView>(R.id.track_name)
    private val artistName = itemView.findViewById<TextView>(R.id.artist_name)
    private val trackTimeMillis = itemView.findViewById<TextView>(R.id.track_time)

    fun bind(model: Track) {

        trackName.text = trimTrailingSpaces(model.trackName)
        artistName.text = trimTrailingSpaces(model.artistName)
        trackTimeMillis.text = formatTrackTime(model.trackTimeMillis)

        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(2))
            .into(poster)
    }

    fun formatTrackTime(durationMillis: Int): String {
        val minutes = durationMillis / 1000 / 60
        val seconds = (durationMillis / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun trimTrailingSpaces(input: String): String {
        return input.trimEnd()
    }

}