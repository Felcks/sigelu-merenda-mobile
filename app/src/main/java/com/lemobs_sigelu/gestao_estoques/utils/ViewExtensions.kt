package com.lemobs_sigelu.gestao_estoques.utils

import android.content.Context
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

    manager?.hideSoftInputFromWindow(this.windowToken, 0)
}