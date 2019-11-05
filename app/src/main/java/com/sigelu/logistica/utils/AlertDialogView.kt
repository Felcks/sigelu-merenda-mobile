package com.sigelu.logistica.utils

import android.app.AlertDialog
import android.view.View
import com.sigelu.logistica.R

class AlertDialogView(
    val alertDialog: AlertDialog,
    val view: View,
    val positiveButtonAcao: () -> Unit,
    val negativeButtonAcao: () -> Unit){

    init{
        view.findViewById<View>(R.id.btn_close).setOnClickListener {
            alertDialog.dismiss()
        }

        view.findViewById<View>(R.id.btn_ok).setOnClickListener {
            positiveButtonAcao()
            alertDialog.dismiss()
        }

        view.findViewById<View>(R.id.btn_cancel).setOnClickListener {
            negativeButtonAcao()
            alertDialog.dismiss()
        }
    }

    fun show(){
        alertDialog.show()
    }
}