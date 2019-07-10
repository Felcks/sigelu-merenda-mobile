package com.lemobs_sigelu.gestao_estoques.extensions_constants

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */

fun String.anoMesDiaToDate(): Date {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleDateFormat.parse(this) as Date
}

fun String.horaMinutoSegundoToDate(): Date {
    val simpleDateFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
    return simpleDateFormat.parse(this) as Date
}

fun String.anoMesDiaHoraMinutoSegundoToDate(): Date {
    if(this.isEmpty())
        return Date()

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd/hh:mm:ss", Locale.getDefault())
    return simpleDateFormat.parse(this) as Date
}

fun String.toDate(): Date {
    return this.anoMesDiaToDate()
}

fun String.createdAtToDate(): Date {
    if(this.isEmpty())
        return Date()

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")
    return simpleDateFormat.parse(this) as Date
}

fun String.tracoSeVazio(): String{
    if(this.isEmpty()){
        return "-"
    }
    return this
}
