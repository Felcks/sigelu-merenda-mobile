package com.sigelu.merenda.ui.cadastra_recebimento

class ItemEstoqueDTO(val itemEstoqueID: Int,
                     val codigo: String,
                     val nomeAlternativo: String,
                     val descricao: String,
                     val unidadeMedida: UnidadeMedidaDTO
)