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

fun String.anoMesDiaToDate(): Date {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleDateFormat.parse(this) as Date
}

fun String.horaMinutoSegundoToDate(): Date {
    val simpleDateFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
    return simpleDateFormat.parse(this) as Date
}

fun String.anoMesDiaHoraMinutoSegundoToDate(): Date {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd/hh:mm:ss", Locale.getDefault())
    return simpleDateFormat.parse(this) as Date
}

fun String.toDate(): Date {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleDateFormat.parse(this) as Date
}

fun Date.getDataCreatedAt(): String{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

fun String.toDateCreatedAt(): Date {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.parse(this) as Date
}

fun Date.toDiaMesAno(): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

fun Date.toHoraMinutoSegundo(): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

@set:BindingAdapter("isVisible")
inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }