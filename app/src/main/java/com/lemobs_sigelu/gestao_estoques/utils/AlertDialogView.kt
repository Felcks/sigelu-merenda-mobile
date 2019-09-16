package com.lemobs_sigelu.gestao_estoques.utils

import android.app.AlertDialog
import android.view.View

class AlertDialogView(
    val alertDialog: AlertDialog,
    val view: View){

    fun show(){
        alertDialog.show()
    }
}