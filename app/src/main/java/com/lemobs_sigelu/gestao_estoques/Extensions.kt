package com.lemobs_sigelu.gestao_estoques

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
