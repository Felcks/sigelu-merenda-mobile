package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo

class ItemEstoqueDTO(val itemEstoqueID: Int,
                     val codigo: String,
                     val nomeAlternativo: String,
                     val descricao: String,
                     val unidadeMedida: UnidadeMedidaDTO
)