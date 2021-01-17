package com.experiment.android.spacexpastlaunchtracker.ui.pastlaunches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.experiment.android.spacexpastlaunchtracker.databinding.ItemPastLaunchBinding
import com.experiment.android.spacexpastlaunchtracker.models.response.PastLaunchResponse
import com.experiment.android.spacexpastlaunchtracker.ui.base.loadImage
import com.experiment.android.spacexpastlaunchtracker.utils.extensions.toDate

class PastLaunchListPagingAdapter(val itemClickListener: ListItemClickListener) :
    PagingDataAdapter<PastLaunchResponse, PastLaunchListPagingAdapter.PastLaunchViewHolder>(
        DIFF_UTIL
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastLaunchViewHolder {
        val binding =
            ItemPastLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PastLaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PastLaunchViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (null != currentItem)
            holder.bind(currentItem)
    }


    inner class PastLaunchViewHolder(private val binding: ItemPastLaunchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PastLaunchResponse) {
            binding.model = model
            binding.ivMissionPatchImage.loadImage(model.links?.missionPathImageUrl)
            binding.cardView.setOnClickListener {
                itemClickListener.onItemClicked(model)
            }
            println("Date Inter " + model.launchDateUtc.toDate())
            binding.executePendingBindings()
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PastLaunchResponse>() {
            override fun areItemsTheSame(
                oldItem: PastLaunchResponse,
                newItem: PastLaunchResponse
            ): Boolean =
                oldItem.flightNumber == newItem.flightNumber

            override fun areContentsTheSame(
                oldItem: PastLaunchResponse,
                newItem: PastLaunchResponse
            ): Boolean =
                oldItem == newItem
        }
    }

    class ListItemClickListener(val itemClickListener: (pastLaunchData: PastLaunchResponse) -> Unit) {
        fun onItemClicked(pastLaunchData: PastLaunchResponse) = itemClickListener(pastLaunchData)
    }

}