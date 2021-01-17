package com.experiment.android.spacexpastlaunchtracker.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.experiment.android.spacexpastlaunchtracker.utils.constants.Constants
import java.text.SimpleDateFormat
import java.util.*

fun SwipeRefreshLayout?.start() {
    this?.apply {
        isRefreshing = true
        isEnabled = false
    }
}

fun SwipeRefreshLayout?.stop() {
    this?.apply {
        isRefreshing = false
        isEnabled = true
    }
}

fun String.append(transform: (String) -> String) = this + transform(this)

fun launchExternalApp(context: Context, url: String?) {
    url?.let {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}

fun String.toDate(): Date {
    val sdfParser = SimpleDateFormat(Constants.API_DATE_FORMAT, Locale.getDefault())
    sdfParser.timeZone = TimeZone.getTimeZone("UTC")
    return sdfParser.parse(this)
}

fun Date.formatTo(dateFormat: String): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(this)
}

