package com.lemobs_sigelu.gestao_estoques.utils

import android.content.Context
import android.content.SharedPreferences
import com.lemobs_sigelu.gestao_estoques.BuildConfig

class AppSharedPreferences {

    companion object {

        private val SESSION = "${BuildConfig.APPLICATION_ID}.session"
        private val KEY_USER_ID = "${BuildConfig.APPLICATION_ID}.user_id"
        private val KEY_USER_TOKEN = "${BuildConfig.APPLICATION_ID}.user_token"
        private val KEY_USER_NAME = "${BuildConfig.APPLICATION_ID}.user_name"
        private val KEY_USER_PERFIL = "${BuildConfig.APPLICATION_ID}.user_perfil"
        private val KEY_FONT_SIZE = "${BuildConfig.APPLICATION_ID}.font_size"
        private val KEY_PRIMEIRA_VEZ = "${BuildConfig.APPLICATION_ID}.primeira_vez"
        private val KEY_NUCLEO_ID = "${BuildConfig.APPLICATION_ID}.nucleo_id"
        private val KEY_NUCLEO_NOME = "${BuildConfig.APPLICATION_ID}.nucleo_nome"
        private val KEY_USER_PERMISSIONS = "${BuildConfig.APPLICATION_ID}.permissoes"

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

        fun getUserId(context: Context) : Int{
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getInt(KEY_USER_ID, -1)
        }
        fun setUserId(context: Context, id: Int){
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(KEY_USER_ID, id)
            editor.apply()
        }


        fun getUserToken(context: Context) : String{
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getString(KEY_USER_TOKEN, "")
        }
        fun setUserToken(context: Context, token: String){
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(KEY_USER_TOKEN, token)
            editor.apply()
        }


        fun getUserFontSize(context: Context) : Float{
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getFloat(KEY_FONT_SIZE, 1f)
        }
        fun setUserFontSize(context: Context, fontSize: Float){
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putFloat(KEY_FONT_SIZE, fontSize)
            editor.apply()
        }


        fun getUserName(context: Context) : String{
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getString(KEY_USER_NAME, "")
        }
        fun setUserName(context: Context, token: String){
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(KEY_USER_NAME, token)
            editor.apply()
        }


        fun getUserPerfil(context: Context) : String{
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getString(KEY_USER_PERFIL, "")
        }
        fun setUserPerfil(context: Context, perfil: String){
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(KEY_USER_PERFIL, perfil)
            editor.apply()
        }

        fun getNucleoID(context: Context) : Int{
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getInt(KEY_NUCLEO_ID, -1)
        }
        fun setNucleoID(context: Context, id: Int){
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(KEY_NUCLEO_ID, id)
            editor.apply()
        }

        fun getNucleoNome(context: Context) : String{
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            return prefs.getString(KEY_NUCLEO_NOME, "")
        }
        fun setNucleoNome(context: Context, token: String){
            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(KEY_NUCLEO_NOME, token)
            editor.apply()
        }




        fun setUserPermissoes(context: Context, permissoes: List<String>){

            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putStringSet(KEY_USER_PERMISSIONS, permissoes.toSet())
            editor.apply()
        }

        fun getUserPermissoes(context: Context): List<String>{

            val prefs: SharedPreferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
            val conjunto = prefs.getStringSet(KEY_USER_PERMISSIONS, setOf())
            return conjunto.toList()
        }
    }


}