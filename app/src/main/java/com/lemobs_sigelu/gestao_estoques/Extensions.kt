package com.lemobs_sigelu.gestao_estoques

import android.content.Context
import android.databinding.BindingAdapter
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

fun Date.getDataFormatada(): String{
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun String.tracoSeVazio(): String{
    if(this.isEmpty()){
        return "-"
    }
    return this
}

fun View.esconderTeclado(){
    val manager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    manager?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun String.toDate(): Date {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return simpleDateFormat.parse(this) as Date
}

@set:BindingAdapter("isVisible")
inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }