package com.armagancivelek.spacex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.armagancivelek.spacex.R
import com.armagancivelek.spacex.databinding.RocketsItemRowBinding
import com.armagancivelek.spacex.model.RocketResponse
import com.armagancivelek.spacex.utils.downloadFromUrl
import com.armagancivelek.spacex.utils.placeholderProgressBar

class RocketsAdapter : RecyclerView.Adapter<RocketsAdapter.RocketViewHolder>() {

    inner class RocketViewHolder(var binding: RocketsItemRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<RocketResponse>() {
        override fun areItemsTheSame(oldItem: RocketResponse, newItem: RocketResponse): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: RocketResponse, newItem: RocketResponse): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        return RocketViewHolder(
            RocketsItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        holder.binding.txtRocketName.text = differ.currentList[position].rocket_name


        if (differ.currentList[position].active) {

            holder.binding.btnActive.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.green
                )
            )
        } else {

            holder.binding.btnActive.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.red
                )
            )
        }

        holder.binding.imageRocket.downloadFromUrl(
            differ.currentList[position].flickr_images.first().toString(),
            placeholderProgressBar(holder.itemView.context)
        )

        holder.binding.imageRocket.setOnClickListener {
            onItemClik?.invoke(differ.currentList[position].rocket_id)

        }


    }

    private var onItemClik: ((String) -> Unit)? = null

    fun setOnClickListener(listener: (String) -> Unit) {
        onItemClik = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}