package com.sigelu.merenda.extensions_constants

import android.content.Context
import androidx.databinding.BindingAdapter
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.changeVisibility(){

    if(this.visibility == View.VISIBLE)
        this.visibility = View.GONE
    else if(this.visibility == View.GONE)
        this.visibility = View.VISIBLE
}

fun View.esconderTeclado(){

    val manager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(this.windowToken, 0)
}

@set:BindingAdapter("isVisible")
inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }