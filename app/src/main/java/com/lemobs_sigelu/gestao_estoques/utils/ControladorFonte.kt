package com.lemobs_sigelu.gestao_estoques.utils

import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics

class ControladorFonte(val activity: AppCompatActivity) {

    private enum class FonteTamanho(val tamanho: Float, val textoTrocarFonte: String) {
        NORMAL(1f, "Aumentar fonte"),
        GRANDE(1.15f, "Diminuir fonte")
    }

    private var fonte = FonteTamanho.NORMAL

    init {
        val tamanhoFonteAtual = AppSharedPreferences.getUserFontSize(this.activity.applicationContext)
        if(tamanhoFonteAtual == 1f){
            fonte = FonteTamanho.NORMAL
        }
        else{
            fonte = FonteTamanho.GRANDE
        }
    }

    fun trocarTamanhoFonte(){

        if(fonte == FonteTamanho.NORMAL){
            fonte = FonteTamanho.GRANDE
            setTamanhoFonte(fonte.tamanho)
        }
        else{
            fonte = FonteTamanho.NORMAL
            setTamanhoFonte(fonte.tamanho)
        }
    }

    fun getTextoTrocarFonte(): String{

        return fonte.textoTrocarFonte
    }

    fun atualizarTamanhoFonte(){

        val size = AppSharedPreferences.getUserFontSize(this.activity.applicationContext)
        setTamanhoFonte(size)
    }

    private fun setTamanhoFonte(size: Float){

        AppSharedPreferences.setUserFontSize(this.activity.applicationContext, size)
        val configuration = activity.resources.configuration
        configuration.fontScale = size

        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        activity.resources.updateConfiguration(configuration, metrics)
    }
}