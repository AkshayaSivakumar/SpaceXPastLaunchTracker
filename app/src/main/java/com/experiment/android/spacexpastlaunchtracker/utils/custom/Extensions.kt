package com.experiment.android.spacexpastlaunchtracker.utils.custom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

fun String.toDate(
    dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

