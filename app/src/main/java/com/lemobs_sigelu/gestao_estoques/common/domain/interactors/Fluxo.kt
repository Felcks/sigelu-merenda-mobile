package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

interface Fluxo {

    fun getPassoAtual(): Int
    fun setPassoAtual(value: Int)

    fun incrimentaPassoAtual()
    fun decrementaPassoAtual()

    fun getMaximoPasso(): Int
    fun setMaximoPasso(value: Int)

    fun getTextoProximoPasso(): String
}