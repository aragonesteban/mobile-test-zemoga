package com.zemoga.shared.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToastErrorMessage(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}