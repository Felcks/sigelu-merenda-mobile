package com.sigelu.logistica.ui.cadastra_recebimento_novo.cadastra_recebimento_3_confirma

import com.sigelu.logistica.ui.cadastra_recebimento_novo.ItemEstoqueDTO

class ItemRecebimentoDTO (val quantidadeEnviada: Double,
                          val itemEstoque: ItemEstoqueDTO,
                          val quantidadeRecebida: Double,
                          val observacao: String)