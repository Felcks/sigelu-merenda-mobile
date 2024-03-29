package com.sigelu.merenda.common.domain.model.accounts

import android.os.Bundle
import com.google.gson.Gson
import com.sigelu.merenda.BuildConfig

class CarregaDados(dados: Bundle?) {
    init {
        DataHolder.setSchemeLauncher(dados?.getString("scheme_launcher") ?: "")

        val usuario : UsuarioAccounts?   = Gson().fromJson(dados?.getString("usuario"), UsuarioAccounts::class.java)

        val ambiente : String? = dados?.getString("modo_ambiente")

        DataHolder.setUsuario(usuario)
        DataHolder.SetAmbienteCorreto(ambiente == BuildConfig.FLAVOR)
    }

    companion object {

        fun limpar() {
            DataHolder.setUsuario(null)
        }
    }
}