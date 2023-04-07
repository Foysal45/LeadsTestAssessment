package com.example.leadsassessment.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


@SuppressLint("SuspiciousIndentation")
fun Context.toast(msg: String?, time: Int = Toast.LENGTH_SHORT) {
    if (!msg.isNullOrEmpty())
    Toast.makeText(this, msg, time).show()
}

