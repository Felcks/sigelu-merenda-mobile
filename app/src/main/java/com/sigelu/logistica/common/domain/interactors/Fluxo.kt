package com.sigelu.logistica.common.domain.interactors

interface Fluxo {

    fun getPassoAtual(): Int
    fun setPassoAtual(value: Int)

    fun incrementaPassoAtual()
    fun decrementaPassoAtual()

    fun getMaximoPasso(): Int
    fun setMaximoPasso(value: Int)

    fun getTextoProximoPasso(): String
}