package com.lemobs_sigelu.gestao_estoques.utils

import android.content.Context
import android.content.SharedPreferences
import com.lemobs_sigelu.gestao_estoques.BuildConfig

class CadastraPedidoSharedPreferences {

    companion object {

        private val SESSION = "${BuildConfig.APPLICATION_ID}.session"
        private val KEY_PEDIDO_ID = "${BuildConfig.APPLICATION_ID}.pedido_id"

        private val KEY_OBRA_DESTINO_ID = "${BuildConfig.APPLICATION_ID}.obra_destino_id"
        private val KEY_MATERIAL_ADICIONADO_ID = "${BuildConfig.APPLICATION_ID}.material_adicionado_id"


        fun getObraDestinoPedidoId(context: Context): Int {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getInt(KEY_OBRA_DESTINO_ID, -1)
        }

        fun setObraDestinoPedidoId(context: Context, id: Int) {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(KEY_OBRA_DESTINO_ID, id)
            editor.apply()
        }


        fun getMaterialAdicionadoId(context: Context): Int {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getInt(KEY_MATERIAL_ADICIONADO_ID, -1)
        }

        fun setMaterialAdicionadoId(context: Context, id: Int) {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(KEY_MATERIAL_ADICIONADO_ID, id)
            editor.apply()
        }
    }
}