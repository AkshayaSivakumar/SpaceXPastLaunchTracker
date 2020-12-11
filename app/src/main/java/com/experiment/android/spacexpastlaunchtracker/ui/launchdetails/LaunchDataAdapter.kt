package com.experiment.android.spacexpastlaunchtracker.ui.launchdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.experiment.android.spacexpastlaunchtracker.R
import com.experiment.android.spacexpastlaunchtracker.databinding.ItemLinkDetailsBinding
import com.experiment.android.spacexpastlaunchtracker.databinding.ItemMainDetailsBinding
import com.experiment.android.spacexpastlaunchtracker.models.MainDetailsModel
import com.experiment.android.spacexpastlaunchtracker.models.ViewType
import com.experiment.android.spacexpastlaunchtracker.models.response.Links
import com.experiment.android.spacexpastlaunchtracker.utils.custom.append

class LaunchDataAdapter(
    private val details: ArrayList<ViewType>,
    private val chipClickListener: ChipClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.VIEW_TYPE_MAIN_DETAILS -> {
                val binding: ItemMainDetailsBinding = MainDetailsViewHolder.from(parent)
                MainDetailsViewHolder(binding)
            }
            ViewType.VIEW_TYPE_LINKS -> {
                val binding: ItemLinkDetailsBinding = LinkDetailsViewHolder.from(parent)
                LinkDetailsViewHolder(binding, chipClickListener)
            }
            else -> {
                val binding: ItemMainDetailsBinding = MainDetailsViewHolder.from(parent)
                MainDetailsViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == ViewType.VIEW_TYPE_MAIN_DETAILS) {
            (holder as MainDetailsViewHolder).bind(details[position] as MainDetailsModel)
        } else {
            (holder as LinkDetailsViewHolder).bind(details[position] as Links)
        }
    }

    override fun getItemCount(): Int {
        return details.count()
    }

    override fun getItemViewType(position: Int): Int {
        return when (details[position]) {
            is MainDetailsModel -> ViewType.VIEW_TYPE_MAIN_DETAILS
            is Links -> ViewType.VIEW_TYPE_LINKS
            else -> ViewType.VIEW_TYPE_MAIN_DETAILS
        }
    }
}

/**
 * ViewHolder for main details
 */
class MainDetailsViewHolder(var binding: ItemMainDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MainDetailsModel) {
        binding.model = model
        binding.tvValue5.text = model.rocketName.append { " , " }.append { model.rocketType }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ItemMainDetailsBinding {
            val inflater = LayoutInflater.from(parent.context)
            return DataBindingUtil.inflate(
                inflater, R.layout.item_main_details, parent, false
            )
        }
    }
}

/**
 * ViewHolder for Links - Chip &  ChipGroup
 */
class LinkDetailsViewHolder(
    var binding: ItemLinkDetailsBinding,
    private var chipClickListener: ChipClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Links) {
        binding.model = model
        binding.clickListener = chipClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ItemLinkDetailsBinding {
            val inflater = LayoutInflater.from(parent.context)
            return DataBindingUtil.inflate(
                inflater, R.layout.item_link_details, parent, false
            )
        }
    }
}

/**
 * Click listener for chip
 */
interface ChipClickListener {
    fun onChipClicked(url: String)
}