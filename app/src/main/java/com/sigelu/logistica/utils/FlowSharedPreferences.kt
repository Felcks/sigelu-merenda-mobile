package com.sigelu.logistica.utils

import android.content.Context
import android.content.SharedPreferences
import com.sigelu.logistica.BuildConfig

class FlowSharedPreferences {

    companion object {

        private val SESSION = "${BuildConfig.APPLICATION_ID}.session"
        private val KEY_PEDIDO_ID = "${BuildConfig.APPLICATION_ID}.pedido_id"
        private val KEY_ENVIO_ID = "${BuildConfig.APPLICATION_ID}.envio_id"
        private val KEY_ITEM_ESTOQUE_ID = "${BuildConfig.APPLICATION_ID}.item_estoque_id"

        //Essa parte é para criação de pedido provavelmente vai para o bd interno

        fun getPedidoId(context: Context): Int {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getInt(KEY_PEDIDO_ID, -1)
        }

        fun setPedidoId(context: Context, id: Int) {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(KEY_PEDIDO_ID, id)
            editor.apply()
        }

        fun getEnvioId(context: Context): Int {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getInt(KEY_ENVIO_ID, -1)
        }

        fun setEnvioId(context: Context, id: Int) {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(KEY_ENVIO_ID, id)
            editor.apply()
        }

        fun getItemEnvioID(context: Context): Int {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getInt(KEY_ITEM_ESTOQUE_ID, -1)
        }

        fun setItemEnvioID(context: Context, id: Int) {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(KEY_ITEM_ESTOQUE_ID, id)
            editor.apply()
        }


    }
}