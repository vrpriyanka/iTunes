package com.ragini.artistdataitunes.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ragini.artistdataitunes.databinding.ItemItunesBinding
import com.ragini.artistdataitunes.model.ITunes
import com.ragini.artistdataitunes.util.Logger

class ITunesAdapter(private var listSearchResults: List<ITunes>) :
    RecyclerView.Adapter<ITunesAdapter.ITunesViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ITunesViewHolder {
        val binding =
            ItemItunesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ITunesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ITunesViewHolder, position: Int) {
        val itemITune = listSearchResults[position]
        Logger.printLog("app_log", "in onBindViewHolder ${itemITune.artistName}")

        val trackPrice = "$" + itemITune.trackPrice
        holder.binding.artistName.text = itemITune.artistName
        holder.binding.trackName.text = itemITune.trackName
        holder.binding.trackPrice.text = trackPrice

        val separated = (itemITune.releaseDate)?.split("T")
        if(separated != null) {
            val releaseDate = separated[0] + " " + separated[1].dropLast(1)
            holder.binding.releaseDate.text = releaseDate
        }

        holder.binding.primaryGenreName.text = itemITune.primaryGenreName
        Glide.with(holder.binding.imageItune.context)
            .load(itemITune.artworkUrl100)
            .centerCrop()
            .into(holder.binding.imageItune)
    }

    override fun getItemCount(): Int {
        return listSearchResults.size
    }

    fun addITunesItems(iTuneItems: List<ITunes>) {
        Logger.printLog("app_log", "in addITunesItems")
        this.listSearchResults = iTuneItems
    }

    inner class ITunesViewHolder(var binding: ItemItunesBinding) :
        RecyclerView.ViewHolder(binding.root)
}