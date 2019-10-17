package com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra

class ObraDTO (val id: Int,
               val nome: String){

    private var selecionado: Boolean = false

    fun seleciona() { selecionado = true }
    fun tiraSelecao() { selecionado = false }
    fun getSelecionado() = selecionado
}