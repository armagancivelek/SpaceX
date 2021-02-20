package com.armagancivelek.spacex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.armagancivelek.spacex.databinding.ItemSliderRowBinding
import com.armagancivelek.spacex.utils.downloadFromUrl
import com.armagancivelek.spacex.utils.placeholderProgressBar

class SliderAdapter(val imageList: ArrayList<String>) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(val binding: ItemSliderRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderAdapter.SliderViewHolder {

        return SliderViewHolder(
            ItemSliderRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false

            )
        )

    }

    override fun onBindViewHolder(holder: SliderAdapter.SliderViewHolder, position: Int) {
        holder.binding.sliderImage.downloadFromUrl(
            imageList.get(position),
            placeholderProgressBar(holder.itemView.context)
        )
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun updateList(newImageList: ArrayList<String>) {
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()

    }
}