package ru.snowmaze.redsofttestproject.ui.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showText(text: CharSequence, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(requireView(), text, length).show()
}

fun Fragment.showText(@StringRes stringRes: Int, length: Int = Snackbar.LENGTH_SHORT) {
    showText(getString(stringRes), length)
}