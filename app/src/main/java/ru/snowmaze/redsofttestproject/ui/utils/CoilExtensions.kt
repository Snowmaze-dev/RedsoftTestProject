package ru.snowmaze.redsofttestproject.ui.utils

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.request.ImageRequest
import ru.snowmaze.redsofttestproject.R

fun ImageRequest.Builder.animatePlaceholder(context: Context): ImageRequest.Builder {
    return placeholder(CircularProgressDrawable(context).apply {
        strokeWidth = context.dpToPx(3)
        centerRadius = context.dpToPx(20)
        setColorSchemeColors(context.getColorFromAttr(R.attr.colorPrimary))
        start()
        setVisible(true, false)
    })
}