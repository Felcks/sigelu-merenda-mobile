package com.sigelu.merenda.ui.cadastra_recebimento.cadastra_recebimento_3_confirma

import com.sigelu.merenda.ui.cadastra_recebimento.ItemEstoqueDTO

class ItemRecebimentoDTO (val quantidadeEnviada: Double,
                          val itemEstoque: ItemEstoqueDTO,
                          val quantidadeRecebida: Double,
                          val observacao: String)