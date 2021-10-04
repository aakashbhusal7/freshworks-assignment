package com.assignment.giphyapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.giphyapp.data.model.SavedGif
import com.assignment.giphyapp.databinding.ItemSavedGifBinding
import com.assignment.giphyapp.ui.viewmodel.GifViewModel
import com.bumptech.glide.Glide

class SavedGifAdapter(
    private val savedGifList: List<SavedGif>,
    private val gifViewModel: GifViewModel
) : RecyclerView.Adapter<SavedGifAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSavedGifBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSavedGifBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.btnRemoveFromFav.setOnClickListener {
            gifViewModel.deleteGif(savedGifList[position].userId)
        }
        holder.itemView.apply {
            Glide.with(context)
                .asGif()
                .load(savedGifList[position].gifImage)
                .into(holder.binding.ivGifLogo)
        }
    }

    override fun getItemCount(): Int {
        return savedGifList.size
    }
}