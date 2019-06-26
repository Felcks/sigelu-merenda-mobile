package com.lemobs_sigelu.gestao_estoques

import android.content.Context
import android.databinding.BindingAdapter
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.lemobs_sigelu.gestao_estoques.utils.HourRangeFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.getDataFormatada(): String{
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.getDataCreatedAt(): String{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

fun Date.toDiaMesAno(): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

fun Date.toDiaMesAnoInvertidaComTracos(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

fun Date.toHoraMinutoSegundo(): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

fun Date.toHoraMinuto(): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

@set:BindingAdapter("isVisible")
inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun EditText.checarHoraValida(): Boolean {

    val inicio = this.text.toString()
    val dateRangeStart: IntArray
    try {
        dateRangeStart = inicio.getHora()

        return !(dateRangeStart[0] > 24 || dateRangeStart[1] >= 60 || dateRangeStart[0] == 24 && dateRangeStart[1] > 0)
    }
    catch (e: Exception) {
        return false
    }

    return false
}

fun EditText.checarDataValida(): Boolean {

    val data = this.text.toString()
    val dataRange: IntArray
    try{
        dataRange = data.getData()

        return !(dataRange[2] >= 2_000 && dataRange[0] <= 31 && dataRange[1] <= 12)
    }
    catch (e: java.lang.Exception){
        return false
    }

    return false

}

private fun String.getHora(): IntArray {
    val date = IntArray(2)

    date[HourRangeFormat.START_HOUR.index] = Integer.parseInt(this.substring(0, 2))
    date[HourRangeFormat.START_MINUTE.index] = Integer.parseInt(this.substring(3, 5))

    return date
}

private fun String.getData(): IntArray {
    val date = IntArray(3)

    date[0] = Integer.parseInt(this.substring(0, 2))
    date[1] = Integer.parseInt(this.substring(3, 5))
    date[2] = Integer.parseInt(this.substring(6, 10))

    return date
}