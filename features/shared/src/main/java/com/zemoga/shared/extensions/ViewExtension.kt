package com.zemoga.shared.extensions

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.zemoga.shared.R

fun View.toggleVisibility(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.showMessage(message: String) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.primary_dark))
    snackbar.setTextColor(ContextCompat.getColor(context, R.color.white))
    snackbar.show()
}