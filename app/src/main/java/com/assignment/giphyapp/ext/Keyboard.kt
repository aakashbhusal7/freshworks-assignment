package com.assignment.giphyapp.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

private var inputMethodManager: InputMethodManager? = null

fun Context.getInputMethodManager(): InputMethodManager? =
    inputMethodManager ?: ContextCompat.getSystemService(
        this,
        InputMethodManager::class.java
    ).also { inputMethodManager = it }

//extension functions to show and hide keyboard for a view

fun View.showKeyboard(flags: Int = InputMethodManager.SHOW_IMPLICIT): Boolean {
    requestFocus()
    return context?.getInputMethodManager()?.showSoftInput(this, flags) ?: false
}

fun View.hideKeyboard(flags: Int = 0) =
    context?.getInputMethodManager()?.hideSoftInputFromWindow(windowToken, flags) ?: false
