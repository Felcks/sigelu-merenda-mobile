package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaCadastroPedidoRepository
import javax.inject.Inject

class ConfirmaCadastroPedidoController @Inject constructor(
    val gerenciaCadastroPedidoRepository: GerenciaCadastroPedidoRepository
)
{
}