package com.experiment.android.spacexpastlaunchtracker.ui.base

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.experiment.android.spacexpastlaunchtracker.R
import com.experiment.android.spacexpastlaunchtracker.utils.constants.Constants
import com.experiment.android.spacexpastlaunchtracker.utils.extensions.formatTo
import com.experiment.android.spacexpastlaunchtracker.utils.extensions.toDate
import com.google.android.material.chip.Chip

/**
 * Extension function to load image into imageview using Glide
 */
fun ImageView.loadImage(imageUrl: String?) {

    if (imageUrl.isNullOrBlank()) {
        Glide.with(this.context)
            .load(R.drawable.ic_broken_image)
            .centerCrop()
            .into(this)
    } else {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .load(imgUri)
            .centerCrop()
            .placeholder(R.drawable.ic_loading_image)
            .error(R.drawable.ic_broken_image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}

/**
 * Binding function to convert int to string and set it to textview
 */
@BindingAdapter("intToString")
fun bindIntToText(textView: TextView, int: Int) {
    textView.text = int.toString()
}

/**
 * Binding function to convert UTC time to Local time
 */
@BindingAdapter("utcToLocal")
fun bindConvertUtcToLocal(textView: TextView, dateStr: String) {
    textView.text =
        if (dateStr.isEmpty() || dateStr.isBlank())
            ""
        else
            dateStr.toDate().formatTo(Constants.DISPLAY_DATE_FORMAT)
}

/**
 * Binding function to set true/false value to textview based on LaunchSuccess
 */
@BindingAdapter("launchSuccess")
fun bindLaunchSuccess(textView: TextView, isLaunchSuccess: Boolean) {
    textView.text = if (isLaunchSuccess) "Yes" else "No"
}

/**
 * Binding function to set visibility of Chip based on the URL availability
 */
@BindingAdapter("visibleIfNotEmpty")
fun bindChipVisibility(chip: Chip, data: String) {
    chip.isVisible = !data.isNullOrBlank()
}

@BindingAdapter("visibleIfNotEmptyTv")
fun bindTextViewVisibility(textView: TextView, data: String?) {
    textView.isVisible = !data.isNullOrBlank()
}