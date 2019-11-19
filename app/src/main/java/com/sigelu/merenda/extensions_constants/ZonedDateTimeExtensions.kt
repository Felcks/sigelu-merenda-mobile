package com.sigelu.merenda.extensions_constants

import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*


fun ZonedDateTime.toDiaMesAno(): String {

    val timeZone = ZoneId.of(TimeZone.getDefault().id)
    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    return this.toInstant()?.atZone(timeZone)?.format(formato) ?: ""
}

fun ZonedDateTime.toAnoMesDiaComTracos(): String {

    val timeZone = ZoneId.of(TimeZone.getDefault().id)
    val formato = DateTimeFormatter.ISO_LOCAL_DATE
    return this.toInstant()?.atZone(timeZone)?.format(formato) ?: ""
}

fun ZonedDateTime.toHoraMinutoSegundo(): String {
    val timeZone = ZoneId.of(TimeZone.getDefault().id)
    val formato = DateTimeFormatter.ofPattern("HH:mm:ss")

    return this.toInstant()?.atZone(timeZone)?.format(formato) ?: ""
}

fun ZonedDateTime.toHoraMinuto(): String {
    val timeZone = ZoneId.of(TimeZone.getDefault().id)
    val formato = DateTimeFormatter.ofPattern("HH:mm")

    return this.toInstant()?.atZone(timeZone)?.format(formato) ?: ""
}