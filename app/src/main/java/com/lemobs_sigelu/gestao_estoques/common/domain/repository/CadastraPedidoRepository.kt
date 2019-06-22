package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import io.reactivex.Observable

class CadastraPedidoRepository {

    fun cadastraPedido(pedidoCadastro: PedidoCadastro): Observable<Unit> {

        return Observable.create { subscriber->


        }
    }
}