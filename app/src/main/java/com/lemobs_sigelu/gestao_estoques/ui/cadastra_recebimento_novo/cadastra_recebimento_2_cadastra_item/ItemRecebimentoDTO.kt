package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_2_cadastra_item

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