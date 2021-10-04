package com.assignment.giphyapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.giphyapp.R
import com.assignment.giphyapp.data.model.GifData
import com.assignment.giphyapp.data.model.SavedGif
import com.assignment.giphyapp.databinding.ItemRemoteGifBinding
import com.assignment.giphyapp.ui.viewmodel.GifViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class RemoteGifAdapter(
    private val gifViewModel: GifViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<RemoteGifAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRemoteGifBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context

    //compare the old item with the newly added items
    private val differCallback = object : DiffUtil.ItemCallback<GifData>() {
        override fun areItemsTheSame(oldItem: GifData, newItem: GifData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GifData,
            newItem: GifData
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemRemoteGifBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = differ.currentList[position]
        holder.itemView.apply {
            //click listener if needed to be accessed by its calling fragment
            setOnClickListener {
                onItemClickListener?.let {
                    it(model)
                }
            }
            model.id?.let {
                //observe if favorite and to the modification on button accordingly
                gifViewModel.checkIfFavorite(it).observe(lifecycleOwner, { isFavorite ->
                    if (isFavorite) {
                        modifyButton(holder, isFavorite)
                        holder.binding.constraintFav.setOnClickListener {
                            model.id.let { gifId ->
                                gifViewModel.deleteGif(
                                    gifId
                                )
                            }
                        }
                    } else {
                        modifyButton(holder, isFavorite)
                        holder.binding.constraintFav.setOnClickListener {
                            model.images?.fixedHeight?.url?.let { image ->
                                gifViewModel.saveGifToFavorite(
                                    SavedGif(
                                        userId = model.id,
                                        gifImage = image
                                    )
                                )
                            }

                        }
                    }
                })
            }
            // apply placeholder and add options for processing of gifs using glide
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.bg_rotate)

            Glide.with(context)
                .asGif()
                .apply(options)
                .load(model.images?.fixedHeight?.url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.binding.ivGifLogo)
        }
    }

    private fun modifyButton(holder: ViewHolder, isFavorite: Boolean) {
        with(holder.binding) {
            if (isFavorite) {
                ivFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_favorite
                    )
                )
                tvBtnText.text =
                    context.resources.getString(R.string.remove_from_favorite)
            } else {
                ivFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_favorite_alt
                    )
                )
                tvBtnText.text =
                    context.resources.getString(R.string.add_to_favorite)
            }
        }

    }

    private var onItemClickListener: ((GifData) -> Unit)? = null

    fun setOnItemClickListener(listener: (GifData) -> Unit) {
        onItemClickListener = listener
    }

}