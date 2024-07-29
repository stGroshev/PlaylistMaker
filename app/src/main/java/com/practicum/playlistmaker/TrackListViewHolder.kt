package com.practicum.playlistmaker


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackListViewHolder ( item: View): RecyclerView.ViewHolder(item){

    private val poster = item.findViewById<ImageView>(R.id.poster)
    private val trackName = item.findViewById<TextView>(R.id.track_name)
    private val artistName = item.findViewById<TextView>(R.id.artist_name)
    private val trackTime = item.findViewById<TextView>(R.id.track_time)

    fun bind(model: Track) {

        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = model.trackTime

        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(2))
            .into(poster)
    }



}