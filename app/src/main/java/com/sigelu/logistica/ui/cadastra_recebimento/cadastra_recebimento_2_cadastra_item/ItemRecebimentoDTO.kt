package com.sigelu.logistica.ui.cadastra_recebimento.cadastra_recebimento_2_cadastra_item

import com.sigelu.logistica.ui.cadastra_recebimento.ItemEstoqueDTO

class ItemRecebimentoDTO (val itemEnvioID: Int,
                          val pedidoEstoqueID: Int,
                          val pedidoEstoqueEnvioID: Int,
                          val itemEstoqueID: Int,
                          val itemEstoque: ItemEstoqueDTO,
                          val observacaoEnvio: String,
                          val quantidadeEnviada: Double){

    var quantidadeRecebida: Double = quantidadeEnviada
    var observacaoRecebimento: String = ""
}