package com.lemobs_sigelu.gestao_estoques.extensions_constants

import android.content.Context
import android.net.ConnectivityManager
import android.widget.EditText
import com.lemobs_sigelu.gestao_estoques.utils.HourRangeFormat

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

fun isConnected(context: Context): Boolean{
    val conmag = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (conmag != null ) {
        conmag.activeNetworkInfo;

        //Verifica internet pela WIFI
        if (conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
            return true
        }

        //Verifica se tem internet móvel
        if (conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
            return true
        }
    }

    return false
}