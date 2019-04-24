package com.lemobs_sigelu.gestao_estoques.utils

import android.content.Context
import android.content.SharedPreferences
import com.lemobs_sigelu.gestao_estoques.BuildConfig

class AppSharedPreferences {

    companion object {

        private val SESSION = "${BuildConfig.APPLICATION_ID}.session"
        private val KEY_PRIMEIRA_VEZ = "${BuildConfig.APPLICATION_ID}.primeira_vez"

        fun getPrimeiraVez(context: Context): Boolean {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getBoolean(KEY_PRIMEIRA_VEZ, true)
        }

        fun setPrimeiraVez(context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putBoolean(KEY_PRIMEIRA_VEZ, false)
            editor.apply()
        }
    }
}