package com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_0_seleciona_obra

class ObraDTO (val id: Int,
               val nome: String){

    private var selecionado: Boolean = false

    fun seleciona() { selecionado = true }
    fun tiraSelecao() { selecionado = false }
    fun getSelecionado() = selecionado
}