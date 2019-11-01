package com.sigelu.logistica.extensions_constants

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */

fun Date.getDataFormatada(): String{
    return this.toDiaMesAno()
}

fun Date.toDiaMesAno(): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

fun Date.toCreatedAt(): String{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.format(this)
}

fun Date.toAnoMesDiaComTracos(): String {
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

