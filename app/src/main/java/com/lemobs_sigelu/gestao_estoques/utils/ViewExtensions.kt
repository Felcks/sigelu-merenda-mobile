package com.lemobs_sigelu.gestao_estoques.utils

import android.view.View

fun View.changeVisibility(){

    if(this.visibility == View.VISIBLE)
        this.visibility = View.GONE
    else if(this.visibility == View.GONE)
        this.visibility = View.VISIBLE
}